package com.diluv.api.route;

import com.diluv.api.route.minecraft.RouterMinecraft;
import com.diluv.api.utils.GameUtilities;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.GAME;

public class RouterGames extends RouterImpl {

    private final Connection conn;
    private final Vertx vertx;

    public RouterGames(Connection conn, Vertx vertx) {
        super(vertx);
        this.conn = conn;
        this.vertx = vertx;

        this.get("/").handler(this::getGames);
        this.mountSubRouter("/minecraft", new RouterMinecraft(this.conn, this.vertx));
    }

    /**
     * @api {get} /games
     * @apiName GetGameList
     * @apiVersion 0.1.0
     * @apiGroup Game
     * @apiUse DefaultReturns
     * @apiSuccess {Integer} data.id The unique id of the game.
     * @apiSuccess {String} data.name The name of the game.
     * @apiSuccess {String} data.website The link to the game website.
     * @apiSuccess {String} data.description A description of the game.
     */
    public void getGames(RoutingContext event) {
        //TODO allow search via name
        DSLContext transaction = DSL.using(this.conn, SQLDialect.MYSQL);

        List<Long> dbGame = transaction.select(GAME.ID)
                .from(GAME)
                .fetch(0, long.class);

        List<Map<String, Object>> gameListOut = new ArrayList<>();
        for (long gameId : dbGame) {
            gameListOut.add(GameUtilities.getGameById(this.conn, gameId));
        }
        ResponseUtilities.asSuccessResponse(event, gameListOut);
    }
}
