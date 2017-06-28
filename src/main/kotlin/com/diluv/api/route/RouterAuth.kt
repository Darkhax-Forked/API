package com.diluv.api.route

import com.diluv.api.models.Tables.*
import com.diluv.api.models.tables.records.UserRecord
import com.diluv.api.utils.*
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.WebClient
import org.apache.commons.validator.routines.EmailValidator
import org.bouncycastle.crypto.generators.OpenBSDBCrypt
import org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.security.SecureRandom
import java.sql.Connection
import java.util.*
import java.util.regex.Pattern
import org.bouncycastle.crypto.params.KeyParameter
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator



class RouterAuth(val conn: Connection, val settings: JsonObject, val JWT: JWTAuth) {
    val USERNAME: Pattern = Pattern.compile("([A-Za-z0-9\\_]+)")

    fun createRouter(router: Router) {
        router.get("/auth/login").handler(getLogin)
        router.get("/auth/mfa").handler(getMFA)
        router.get("/auth/refreshToken").handler(getRefreshToken)

        router.post("/auth/register").handler(postRegister)
    }

    fun createAccessToken(userId: Long, username: String): Map<String, Any> {
        val jwtData = JsonObject().apply {
            put("userId", userId)
            put("username", username)
            put("randomData", UUID.randomUUID().toString())
        }

        val token = encodeToken(JWT = JWT, data = jwtData, issuer = "token", expiresInMinutes = 60)
        val expires = getExpireFromToken(token)

        val refreshToken = encodeToken(JWT = JWT, data = jwtData, issuer = "refreshToken", expiresInMinutes = 60 * 24 * 60)
        val refreshTokenExpires = getExpireFromToken(refreshToken)

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
                "tokenExpires" to expires,
                "refreshToken" to refreshToken,
                "refreshTokenExpires" to refreshTokenExpires
        )
    }

    val getLogin = Handler<RoutingContext> { event ->
        val req = event.request()
        val inputUsername = req.getParam("username")
        val inputEmail = req.getParam("email")
        val inputPassword = req.getParam("password")
        val errorMessage = arrayListOf<String>()

        if (inputUsername == null && inputEmail == null)
            errorMessage.add("Username or email is needed to login")
        if (inputUsername != null && inputEmail != null)
        //TODO
            errorMessage.add("TODO")
        if (inputPassword == null)
            errorMessage.add("Password is needed to login")
        if (inputUsername != null && !validUsername(inputUsername))
            errorMessage.add("Username is not valid")
        if (inputEmail != null && !EmailValidator.getInstance(false).isValid(inputEmail))
            errorMessage.add("Email is not valid")

        val transaction = DSL.using(conn, SQLDialect.MYSQL)
        if (errorMessage.size == 0) {
            val user: UserRecord
            //TODO Catch TooManyRowsException
            if (inputUsername != null) {
                user = transaction.selectFrom(USER)
                        .where(USER.USERNAME.eq(inputUsername)).fetchOne()
            } else {
                user = transaction.selectFrom(USER)
                        .where(USER.EMAIL.eq(inputEmail)).fetchOne()
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
                        val token = encodeToken(JWT = JWT, data = jwtData, issuer = "mfaToken", expiresInMinutes = 10)
                        val expires = getExpireFromToken(token)

                        //TODO Check unique
                        transaction.insertInto(AUTHMFATOKEN, AUTHMFATOKEN.USERID, AUTHMFATOKEN.TOKEN)
                                .values(userId, token)
                                .execute()

                        val tokenData = mapOf(
                                "mfa" to true,
                                "token" to token,
                                "tokenExpires" to expires
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
    }

    val getMFA = Handler<RoutingContext> { event ->
        val token = event.getAuthorizationToken()
        if (token != null) {
            if (!isExpired(token)) {
                val transaction = DSL.using(conn, SQLDialect.MYSQL)
                val mfaToken = transaction.select(AUTHMFATOKEN.TOKEN)
                        .from(AUTHMFATOKEN)
                        .where(AUTHMFATOKEN.TOKEN.eq(token))
                        .fetchOne()

                if (mfaToken != null) {
                    val payload = getPayload(token)
                    val username = payload.getString("username")
                    val userId = payload.getLong("userId")

                    transaction.delete(AUTHMFATOKEN)
                            .where(AUTHMFATOKEN.TOKEN.eq(token))
                            .execute()

                    transaction.insertInto(ANALYTICSAUTHMFATOKEN, ANALYTICSAUTHMFATOKEN.USERID, ANALYTICSAUTHMFATOKEN.TOKEN)
                            .values(userId, token)
                            .execute()

                    event.asSuccessResponse(createAccessToken(userId, username))
                } else
                    event.asErrorResponse(401, "MFA Token not found")
            } else
                event.asErrorResponse(401, "MFA Token has expired")
        } else
            event.asErrorResponse(401, "Incorrect authorization header")
    }

    val getRefreshToken = Handler<RoutingContext> { event ->
        val refreshToken = event.getAuthorizationToken()
        if (refreshToken != null) {
            if (!isExpired(refreshToken)) {
                val transaction = DSL.using(conn, SQLDialect.MYSQL)

                val tokenInfo = transaction.select(AUTHACCESSTOKEN.TOKEN)
                        .from(AUTHACCESSTOKEN)
                        .where(AUTHACCESSTOKEN.REFRESHTOKEN.eq(refreshToken))
                        .fetchOne()

                if (tokenInfo != null) {
                    val payload = getPayload(refreshToken)
                    val username = payload.getString("username")
                    val userId = payload.getLong("userId")

                    transaction.batch(
                            transaction.delete(AUTHACCESSTOKEN)
                                    .where(AUTHACCESSTOKEN.REFRESHTOKEN.eq(refreshToken)),

                            transaction.insertInto(ANALYTICSAUTHACCESSTOKEN, ANALYTICSAUTHACCESSTOKEN.USERID, ANALYTICSAUTHACCESSTOKEN.TOKEN, ANALYTICSAUTHACCESSTOKEN.REFRESHTOKEN)
                                    .values(userId, tokenInfo.get(AUTHACCESSTOKEN.TOKEN), refreshToken)
                    ).execute()

                    event.asSuccessResponse(createAccessToken(userId, username))
                } else
                    event.asErrorResponse(401, "Refresh Token not found")
            } else
                event.asErrorResponse(401, "Refresh Token has expired")
        } else
            event.asErrorResponse(401, "Incorrect authorization header")
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

                    verify(client, settings.getString("recaptchaSecretKey"), recaptchaResponse, { ar ->
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
