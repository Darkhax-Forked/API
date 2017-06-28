package com.diluv.api

import com.diluv.api.route.*
import com.diluv.api.utils.asErrorResponse
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import java.sql.Connection
import java.sql.DriverManager


/**
 * @apiDefine DefaultReturns
 *
 * @apiSuccess {Object} data The data object.
 *
 * @apiError {string} errorMessage The error message.
 * @apiError {number} status The status code of the request
 */
class DiluvAPI(val settings: JsonObject, val conn: Connection) : AbstractVerticle() {

    override fun start(fut: Future<Void>?) {
        val JWT = JWTAuth.create(vertx, JsonObject())

        // Create a router object.
        val router = Router.router(vertx)

        val v1 = Router.router(vertx)

        RouterAuth(conn, settings, JWT).createRouter(v1)
        RouterGames(conn).createRouter(v1)
        RouterProjects(conn).createRouter(v1)
        RouterUsers(conn).createRouter(v1)

        router.mountSubRouter("/v1", v1)
        router.route("/*").handler(StaticHandler.create("apidoc"))

        router.get().failureHandler { ctx ->
            if (ctx.statusCode() == 404) {
                ctx.asErrorResponse(404, "Endpoint not found")
            } else {
                ctx.next()
            }
        }
        vertx.createHttpServer()
                .requestHandler { router.accept(it) }
                .listen(
                        config().getInteger("http.port", 8080),
                        { result ->
                            if (result.succeeded()) {
                                System.out.println("API started on port 8080")
                                fut?.complete()
                            } else {
                                fut?.fail(result.cause())
                            }
                        }
                )
    }
}

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    vertx.fileSystem().readFile("settings.json") { event ->
        if (event.succeeded()) {
            val settings = JsonObject(event.result().toString())

            val host = settings.getString("host")
            val port = settings.getInteger("port")
            val database = settings.getString("database")
            val user = settings.getString("user")
            val password = settings.getString("password")

            val url = "jdbc:mysql://$host:$port/$database?createDatabaseIfNotExist=true"
            val conn = DriverManager.getConnection(url, user, password)

            vertx.deployVerticle(DiluvAPI(settings, conn))
        } else {
            System.err.println("Error reading settings.json file!")
        }
    }
}
