package com.diluv.api.route

import com.diluv.api.models.Tables.USER
import com.diluv.api.models.Tables.USERBETAKEY
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
        router.get("/users/:id").handler(getUserById)
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

            val users = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATEDAT)
                    .from(USER)
                    .fetch()

            val userListOut = users.map {
                mapOf(
                        "id" to it.get(USER.ID),
                        "username" to it.get(USER.USERNAME),
                        "avatar" to it.get(USER.AVATAR),
                        "createdAt" to it.get(USER.CREATEDAT)
                )
            }

            event.asSuccessResponse(userListOut)
        } catch (e: Exception) {
            e.printStackTrace()
            event.asErrorResponse(500, "Internal Error")
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
    val getUserById = Handler<RoutingContext> { event ->
        val id = event.request().getParam("id")
        try {
            var userId: Long? = null
            if (id == "me") {
                val token = event.getAuthorizationToken()
                if (token != null) {
                    if (isTokenValid(conn, token)) {
                        userId = getPayload(token).getLong("userId")
                    } else {
                        event.asErrorResponse(404, "")
                    }
                } else {
                    event.asErrorResponse(404, "")
                }
            } else
                userId = id.toLongOrNull()

            if (userId != null) {
                val transaction = DSL.using(conn, SQLDialect.MYSQL)

                val user = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATEDAT)
                        .from(USER)
                        .where(USER.ID.eq(userId))
                        .fetchOne()

                if (user != null) {
                    val userOut = mapOf<String, Any>(
                            "id" to user.get(USER.ID),
                            "username" to user.get(USER.USERNAME),
                            "avatar" to user.get(USER.AVATAR),
                            "createdAt" to user.get(USER.CREATEDAT)
                    )
                    event.asSuccessResponse(userOut)
                } else {
                    event.asErrorResponse(404, "User not found")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            event.asErrorResponse(500, "Internal Error")
        }
    }

    val postCreateBetaKeys = Handler<RoutingContext> { event ->
        val req = event.request()
        req.isExpectMultipart = true
        val token = event.getAuthorizationToken()
        if (token != null) {

            if (isTokenValid(conn, token)) {
                val payload = getPayload(token)
                val creationUserId = payload.getLong("userId")
                val username = payload.getString("username")
                //TODO Move away from hardcoded

                if (username == "lclc98") {
                    req.endHandler({
                        val userId = req.getFormAttribute("userId").toLongOrNull()
                        val quantity = req.getFormAttribute("quantity")?.toLong() ?: 1
                        if (userId != null) {
                            val random = SecureRandom()
                            val keyList = arrayListOf<String>()
                            //TODO Check duplicates

                            val transaction = DSL.using(conn, SQLDialect.MYSQL)

                            val insertInto = transaction.insertInto(USERBETAKEY, USERBETAKEY.USERID, USERBETAKEY.BETAKEY, USERBETAKEY.CREATIONUSERID)

                            for (i in 0 until quantity) {
                                val key = BigInteger(130, random).toString(32)
                                insertInto.values(userId, key, creationUserId)
                                keyList.add(key)
                            }
                            insertInto.execute()
                            event.asSuccessResponse(keyList)
                        } else {
                            event.asErrorResponse(400, "The target userId is needed for this request")
                        }
                    })
                } else {
                    event.asErrorResponse(403, "User not authorized to make request")
                }
            } else {
                event.asErrorResponse(403, "Token not authorized to make request")
            }
        } else {
            event.asErrorResponse(401, "Token is needed to make this request")
        }
    }
}