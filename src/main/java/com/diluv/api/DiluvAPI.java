package com.diluv.api;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import com.diluv.api.route.RouterAuth;
import com.diluv.api.route.RouterGames;
import com.diluv.api.route.RouterUsers;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @apiDefine DefaultReturns
 * @apiSuccess {Object} data The data object.
 * @apiError {string} errorMessage The error message.
 * @apiError {number} status The status code of the request
 */
public class DiluvAPI extends AbstractVerticle {

    private Connection conn;

    public DiluvAPI(Connection conn) {
        this.conn = conn;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        String host = System.getenv("dbHost");
        String port = System.getenv("dbPort");
        String database = System.getenv("database");
        String user = System.getenv("dbUsername");
        String password = System.getenv("dbPassword");

        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);
        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            vertx.deployVerticle(new DiluvAPI(conn));
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO Shut down and pass it to the reporting system
        }

    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        // Create a router object.
        Router router = Router.router(this.vertx);
        Router apiV1 = Router.router(this.vertx);

        apiV1.mountSubRouter("/auth", new RouterAuth(this.conn, this.vertx));
        apiV1.mountSubRouter("/games", new RouterGames(this.conn, this.vertx));
        apiV1.mountSubRouter("/users", new RouterUsers(this.conn, this.vertx));

        router.mountSubRouter("/v1", apiV1);

        router.get("/teapot").handler(event -> {
            HttpServerResponse res = event.response();
            res.setStatusCode(Errors.I_AM_A_TEAPOT.statusCode);
            res.end("The server refuses the attempt to brew coffee with a teapot.");
        });

        router.options().handler(event -> {
            //TODO Implement proper preflight checks
            ResponseUtilities.asSuccessResponse(event, new ArrayList<>());
        });

        router.route().handler(event -> {
            event.fail(404);
        }).failureHandler(event -> {
            if (event.statusCode() == 404) {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.NOT_FOUND);
            } else {
                event.next();
            }
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", Integer.parseInt(System.getenv("port"))), event -> {
                    if (event.succeeded()) {
                        System.out.println("API started on port " + System.getenv("port"));
                        startFuture.complete();
                    } else {
                        startFuture.fail(event.cause());
                    }
                });

    }
}