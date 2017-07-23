package com.diluv.api

import com.diluv.api.route.RouterAuth
import com.diluv.api.route.RouterGames
import com.diluv.api.route.RouterProjects
import com.diluv.api.route.RouterUsers
import com.diluv.api.utils.asErrorResponse
import com.diluv.api.utils.asSuccessResponse
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * @apiDefine DefaultReturns
 *
 * @apiSuccess {Object} data The data object.
 *
 * @apiError {string} errorMessage The error message.
 * @apiError {number} status The status code of the request
 */
class DiluvAPI(val conn: Connection) : AbstractVerticle() {

    override fun start(fut: Future<Void>?) {
        // Create a router object.
        val router = Router.router(vertx)

        val v1 = Router.router(vertx)

        RouterAuth(conn).createRouter(v1)
        RouterGames(conn).createRouter(v1)
        RouterProjects(conn).createRouter(v1)
        RouterUsers(conn).createRouter(v1)

        router.mountSubRouter("/v1", v1)

        router.options().handler { ctx ->
            ctx.asSuccessResponse(listOf())
        }
        router.route().failureHandler { ctx ->
            if (ctx.statusCode() == 404) {
                ctx.asErrorResponse(404, "Endpoint not found")
            } else if (ctx.statusCode() == 500) {
                ctx.asErrorResponse(500, "Internal Server Error")
            } else {
                ctx.next()
            }
        }
        vertx.createHttpServer()
                .requestHandler { router.accept(it) }
                .listen(
                        config().getInteger("http.port", System.getenv("port").toIntOrNull()),
                        { result ->
                            if (result.succeeded()) {
                                System.out.println("API started on port " + System.getenv("port"))
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
    val host = System.getenv("dbHost")
    val port = System.getenv("dbPort")
    val database = System.getenv("database")
    val user = System.getenv("dbUsername")
    val password = System.getenv("dbPassword")

    val url = "jdbc:mysql://$host:$port/$database"
    try {
        val conn = DriverManager.getConnection(url, user, password)

        vertx.deployVerticle(DiluvAPI(conn))
    } catch(e: SQLException) {
        e.printStackTrace()
        //TODO Shut down and pass it to the reporting system
    }
}
