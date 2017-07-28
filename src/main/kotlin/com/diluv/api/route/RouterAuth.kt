package com.diluv.api.route

import com.diluv.api.models.Tables.*
import com.diluv.api.models.tables.records.UserRecord
import com.diluv.api.utils.*
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.WebClient
import org.apache.commons.validator.routines.EmailValidator
import org.bouncycastle.crypto.generators.OpenBSDBCrypt
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.security.SecureRandom
import java.sql.Connection
import java.util.*
import java.util.regex.Pattern

class RouterAuth(val conn: Connection) {
    val USERNAME: Pattern = Pattern.compile("([A-Za-z0-9\\_]+)")

    fun createRouter(router: Router) {
        router.post("/auth/login").handler(postLogin)
        router.post("/auth/mfa").handler(getMFA)
        router.post("/auth/refreshToken").handler(getRefreshToken)

        router.post("/auth/register").handler(postRegister)
    }

    fun createAccessToken(userId: Long, username: String): Map<String, Any> {
        val jwtData = JsonObject().apply {
            put("userId", userId)
            put("username", username)
            put("permissions", 1)
        }

        val jwtToken = encodeToken(data = jwtData, issuer = "token", expiresInMinutes = 60)
        val token = jwtToken.toString()

        val jwtRefreshToken = encodeToken(data = jwtData, issuer = "refreshToken", expiresInDays = 60)
        val refreshToken = jwtRefreshToken.toString()

        val transaction = DSL.using(conn, SQLDialect.MYSQL)
        val count: Int = transaction.selectCount()
                .from(AUTHACCESSTOKEN)
                .where(AUTHACCESSTOKEN.TOKEN.eq(token).and(AUTHACCESSTOKEN.REFRESHTOKEN.eq(refreshToken)))
                .fetchOne(0, Int::class.java)

        if (count > 0) {
            //TODO Generate new tokens and refresh
        } else {
            transaction.insertInto(AUTHACCESSTOKEN, AUTHACCESSTOKEN.USERID, AUTHACCESSTOKEN.TOKEN, AUTHACCESSTOKEN.REFRESHTOKEN)
                    .values(userId, token, refreshToken)
                    .execute()
        }
        return mapOf(
                "token" to token,
                "tokenExpires" to jwtToken.getExpires(),
                "refreshToken" to refreshToken,
                "refreshTokenExpires" to jwtRefreshToken.getExpires()
        )
    }

    val postLogin = Handler<RoutingContext> { event ->
        val req = event.request()
        req.isExpectMultipart = true

        req.endHandler({
            val inputUsernameEmail = req.getFormAttribute("usernameEmail")
            val inputPassword = req.getFormAttribute("password")
            val errorMessage = arrayListOf<String>()

            if (inputUsernameEmail == null)
                errorMessage.add("Username or email is needed to login")
            if (inputPassword == null)
                errorMessage.add("Password is needed to login")

            var isUsername = true
            if (EmailValidator.getInstance(false).isValid(inputUsernameEmail))
                isUsername = false

            val transaction = DSL.using(conn, SQLDialect.MYSQL)
            if (errorMessage.size == 0) {
                val user: UserRecord
                //TODO Catch TooManyRowsException
                if (isUsername) {
                    user = transaction.selectFrom(USER)
                            .where(USER.USERNAME.eq(inputUsernameEmail)).fetchOne()
                } else {
                    user = transaction.selectFrom(USER)
                            .where(USER.EMAIL.eq(inputUsernameEmail)).fetchOne()
                }

                if (user != null) {
                    if (OpenBSDBCrypt.checkPassword(user.password, inputPassword.toCharArray())) {
                        val userId = user.id
                        val username = user.username
                        if (user.mfaenabled) {
                            val jwtData = JsonObject().apply {
                                put("userId", userId)
                                put("username", username)
                                put("randomData", UUID.randomUUID().toString())
                            }
                            val jwtToken = encodeToken(data = jwtData, issuer = "mfaToken", expiresInMinutes = 10)
                            val token = jwtToken.toString()
                            //TODO Check unique
                            transaction.insertInto(AUTHMFATOKEN, AUTHMFATOKEN.USERID, AUTHMFATOKEN.TOKEN)
                                    .values(userId, token)
                                    .execute()

                            val tokenData = mapOf(
                                    "mfa" to true,
                                    "token" to token,
                                    "tokenExpires" to jwtToken.getExpires()
                            )

                            event.asSuccessResponse(tokenData)
                        } else {
                            event.asSuccessResponse(createAccessToken(userId, username))
                        }
                    } else {
                        event.asErrorResponse(401, "The password is incorrect")
                    }
                } else {
                    event.asErrorResponse(401, "The user doesnt\'t exist")
                }
            } else {
                event.asErrorResponse(400, errorMessage)
            }
        })
    }

    val getMFA = Handler<RoutingContext> { event ->
        val token = event.getAuthorizationToken(conn, true)
        if (token != null) {
            val tokenText = token.toString()
            val transaction = DSL.using(conn, SQLDialect.MYSQL)
            val mfaToken = transaction.select(AUTHMFATOKEN.TOKEN)
                    .from(AUTHMFATOKEN)
                    .where(AUTHMFATOKEN.TOKEN.eq(tokenText))
                    .fetchOne()

            if (mfaToken != null) {
                val username = token.data.getString("username")
                val userId = token.data.getLong("userId")

                transaction.delete(AUTHMFATOKEN)
                        .where(AUTHMFATOKEN.TOKEN.eq(tokenText))
                        .execute()

                transaction.insertInto(ANALYTICSAUTHMFATOKEN, ANALYTICSAUTHMFATOKEN.USERID, ANALYTICSAUTHMFATOKEN.TOKEN)
                        .values(userId, tokenText)
                        .execute()

                event.asSuccessResponse(createAccessToken(userId, username))
            } else
                event.asErrorResponse(401, "MFA Token not found")
        }
        //TODO Fix token valid call DB
    }

    val getRefreshToken = Handler<RoutingContext> { event ->
        //TODO DB valid check fix
        val refreshToken = event.getAuthorizationToken(conn, false)
        if (refreshToken != null) {
            val transaction = DSL.using(conn, SQLDialect.MYSQL)

            val refreshTokeText = refreshToken.toString()
            val tokenInfo = transaction.select(AUTHACCESSTOKEN.TOKEN)
                    .from(AUTHACCESSTOKEN)
                    .where(AUTHACCESSTOKEN.REFRESHTOKEN.eq(refreshTokeText))
                    .fetchOne()

            if (tokenInfo != null) {
                val username = refreshToken.data.getString("username")
                val userId = refreshToken.data.getLong("userId")

                transaction.batch(
                        transaction.delete(AUTHACCESSTOKEN)
                                .where(AUTHACCESSTOKEN.REFRESHTOKEN.eq(refreshTokeText)),

                        transaction.insertInto(ANALYTICSAUTHACCESSTOKEN, ANALYTICSAUTHACCESSTOKEN.USERID, ANALYTICSAUTHACCESSTOKEN.TOKEN, ANALYTICSAUTHACCESSTOKEN.REFRESHTOKEN)
                                .values(userId, tokenInfo.get(AUTHACCESSTOKEN.TOKEN), refreshTokeText)
                ).execute()

                event.asSuccessResponse(createAccessToken(userId, username))
            } else
                event.asErrorResponse(401, "Refresh Token not found")
        }
    }

    val postRegister = Handler<RoutingContext> { event ->
        println(event.request().remoteAddress().host())
        val req = event.request()
        req.isExpectMultipart = true

        req.endHandler({
            val email = req.getFormAttribute("email")
            val username = req.getFormAttribute("username")
            val password = req.getFormAttribute("password")
            val passwordConfirm = req.getFormAttribute("passwordConfirm")
            val recaptchaResponse = req.getFormAttribute("g-recaptcha-response")
            val betaKey = req.getFormAttribute("betaKey")

            val errorMessage = arrayListOf<String>()

            if (email == null)
                errorMessage.add("Registering requires an email")
            else if (!EmailValidator.getInstance(false).isValid(email))
                errorMessage.add("Invalid email address")
            if (username == null)
                errorMessage.add("Registering requires a username")
            else if (!validUsername(username))
                errorMessage.add("Username is not valid")
            if (password == null)
                errorMessage.add("Registering requires a password")
            if (passwordConfirm == null)
                errorMessage.add("Registering requires password confirmation")
            else if (password != passwordConfirm)
                errorMessage.add("Password and Password confirm must match")
            else if (!validPassword(password))
                errorMessage.add("Password is not valid")
            if (recaptchaResponse == null)
                errorMessage.add("Registering requires a recaptcha response")
            if (betaKey == null)  // Beta Temporary
                errorMessage.add("Registering in beta needs a key")

            if (errorMessage.size == 0) {
                val transaction = DSL.using(conn, SQLDialect.MYSQL)
                val user = transaction.select(USER.ID, USER.EMAIL, USER.USERNAME)
                        .from(USER)
                        .where(USER.EMAIL.eq(email).or(USER.USERNAME.eq(username)))
                        .fetchOne()
                if (user != null) {
                    if (user.get(USER.EMAIL) == email)
                        event.asErrorResponse(400, "Email is already used, please use a different email")
                    if (user.get(USER.USERNAME) == username)
                        event.asErrorResponse(400, "Username is already used, please use a different username")
                } else {
                    val client = WebClient.create(event.vertx())

                    verify(client, System.getenv("recaptchaSecretKey"), recaptchaResponse, { ar ->
                        if (ar.succeeded()) {
                            // Obtain response
                            val response = ar.result()
                            val body = response.bodyAsJsonObject()
                            if (body.getBoolean("success")) {
                                val salt = ByteArray(16)
                                SecureRandom().nextBytes(salt)
                                val passwordHash = OpenBSDBCrypt.generate(password.toCharArray(), salt, 10)
                                val userResults = transaction.insertInto(USER, USER.EMAIL, USER.USERNAME, USER.PASSWORD, USER.AVATAR)
                                        .values(email, username, passwordHash, "")
                                        .returning(USER.ID)
                                        .fetchOne()

                                event.asSuccessResponse(createAccessToken(userResults.get(USER.ID), username))
                            } else {
                                event.asErrorResponse(400, "Recaptcha is not valid")
                            }
                        } else {
                            //TODO
                            println("Something went wrong " + ar.cause().localizedMessage)
                        }
                    })
                }
            } else {
                event.asErrorResponse(400, errorMessage)
            }
        })
    }

    fun validUsername(username: String): Boolean {

        if (username.length < 6 || username.length > 20)
            return false
        return USERNAME.matcher(username).matches()
    }

    fun validPassword(password: String): Boolean {

        //TODO Check to see if password is valid
        if (password.length < 6 || password.length > 50)
            return false
        return true
    }
}
