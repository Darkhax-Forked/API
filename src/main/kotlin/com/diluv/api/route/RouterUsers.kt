package com.diluv.api.route

import com.diluv.api.error.Errors
import com.diluv.api.jwt.JWT
import com.diluv.api.jwt.isTokenValid
import com.diluv.api.models.Tables.USER
import com.diluv.api.models.Tables.USER_BETA_KEY
import com.diluv.api.permission.user.UserPermissionType
import com.diluv.api.utils.*
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.math.BigInteger
import java.security.SecureRandom
import java.sql.Connection


class RouterUsers(val conn: Connection) {

    fun createRouter(router: Router) {
        router.get("/users").handler(getUsers)
        router.get("/users/:username").handler(getUserByUsername)
        router.get("/users/:username/settings").handler(getUserSettingsByUsername)
        router.post("/users/generateBetaKey").handler(postCreateBetaKeys)
    }

    /**
     * @api {get} /users
     * @apiName GetUserList
     * @apiVersion 0.1.0
     * @apiGroup User
     *
     * @apiUse DefaultReturns
     * @apiSuccess {String} data.id The unique snowflake of the user.
     * @apiSuccess {String} data.username The username of the user.
     * @apiSuccess {String} data.avatar The link to the avatar of the user.
     * @apiSuccess {String} data.createdAt The epoch the user was created at.
     */
    val getUsers = Handler<RoutingContext> { event ->
        try {
            val transaction = DSL.using(conn, SQLDialect.MYSQL)

            val users = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT)
                    .from(USER)
                    .fetch()

            val userListOut = users.map {
                conn.getUserByUserId(it.get(USER.ID), false)
            }

            event.asSuccessResponse(userListOut)
        } catch (e: Exception) {
            e.printStackTrace()
            event.asErrorResponse(Errors.INTERNAL_SERVER_ERROR, "Internal Error")
        }
    }

    /**
     * @api {get} /users/:id
     * @apiName GetUser
     * @apiVersion 0.1.0
     * @apiGroup User
     *
     * @apiUse DefaultReturns
     * @apiParam {String} id The unique snowflake of a user.
     * @apiSuccess {String} data.id The unique snowflake of the user.
     * @apiSuccess {String} data.username The username of the user.
     * @apiSuccess {String} data.avatar The link to the avatar of the user.
     * @apiSuccess {String} data.createdAt The epoch the user was created at.
     */
    val getUserByUsername = Handler<RoutingContext> { event ->
        val username = event.request().getParam("username")

        var userId: Long? = null
        var authorized = false
        val token = event.getAuthorizationToken()
        if (token != null) {
            if (conn.isTokenValid(token)) {
                val jwt = JWT(token)
                if (!jwt.isExpired()) {
                    val tokenUsername = jwt.data.getString("username")
                    val tokenUserId = jwt.data.getLong("userId")
                    if (tokenUserId != null && (username == "me" || username == tokenUsername)) {
                        userId = tokenUserId
                        authorized = true
                    }
                }
            }
        }

        if (!authorized) {
            val transaction = DSL.using(conn, SQLDialect.MYSQL)

            val dbUserId = transaction.select(USER.ID)
                    .from(USER)
                    .where(USER.USERNAME.eq(username))
                    .fetchOne(0, Long::class.java)

            userId = dbUserId
        }

        if (userId != null) {
            event.asSuccessResponse(conn.getUserByUserId(userId, authorized))
        } else {
            event.asErrorResponse(Errors.NOT_FOUND, "User not found")
        }
    }

    val getUserSettingsByUsername = Handler<RoutingContext> { event ->
        val username = event.request().getParam("username")

        val token = event.getAuthorizationToken()
        if (token != null) {
            if (conn.isTokenValid(token)) {
                val jwt = JWT(token)
                if (!jwt.isExpired()) {
                    val tokenUsername = jwt.data.getString("username")
                    val tokenUserId = jwt.data.getLong("userId")
                    if (tokenUserId != null && (username == "me" || username == tokenUsername)) {
                        val userOut = conn.getUserSettingsByUserId(tokenUserId)
                        event.asSuccessResponse(userOut)
                    }
                }
            }
        } else {
            event.asErrorResponse(Errors.UNAUTHORIZED, "User token not authorized for this request")
        }
    }


    val postCreateBetaKeys = Handler<RoutingContext> { event ->
        val req = event.request()
        req.isExpectMultipart = true
        val token = event.getAuthorizationToken()
        if (token != null) {
            val jwt = JWT(token)
            if (!jwt.isExpired()) {
                val payload = jwt.data
                val creationUserId = payload.getLong("userId")
                val permission = payload.getInteger("permission", 1)

                //TODO Move away from hardcoded

                if (UserPermissionType.CREATE_BETA_KEYS.hasPermission(permission)) {
                    req.endHandler({
                        val userId = req.getFormAttribute("userId").toLongOrNull()
                        val quantity = req.getFormAttribute("quantity")?.toLong() ?: 1
                        if (userId != null) {
                            val random = SecureRandom()
                            val keyList = arrayListOf<String>()
                            //TODO Check duplicates

                            val transaction = DSL.using(conn, SQLDialect.MYSQL)

                            val insertInto = transaction.insertInto(USER_BETA_KEY, USER_BETA_KEY.USER_ID, USER_BETA_KEY.BETA_KEY, USER_BETA_KEY.CREATION_USER_ID)

                            for (i in 0 until quantity) {
                                val key = BigInteger(130, random).toString(32)
                                insertInto.values(userId, key, creationUserId)
                                keyList.add(key)
                            }
                            insertInto.execute()
                            event.asSuccessResponse(keyList)
                        } else {
                            event.asErrorResponse(Errors.BAD_REQUEST, "The target userId is needed for this request")
                        }
                    })
                } else {
                    event.asErrorResponse(Errors.FORBIDDEN, "User not authorized to make request")
                }
            }
        }
    }
}