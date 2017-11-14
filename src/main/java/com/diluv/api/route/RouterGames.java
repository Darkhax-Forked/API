package com.diluv.api.route;

import com.diluv.api.DiluvAPI;
import com.diluv.api.route.minecraft.RouterMinecraft;
import com.diluv.api.utils.Game;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.GAME;

public class RouterGames extends RouterImpl {

    private final Vertx vertx;

    public RouterGames(Vertx vertx) {
        super(vertx);
        this.vertx = vertx;

        this.get("/").handler(this::getGames);
        this.mountSubRouter("/minecraft", new RouterMinecraft(this.vertx));
    }

    public void getGames(RoutingContext event) {
        //TODO allow search via name
        List<Long> dbGame = DiluvAPI.getDSLContext().select(GAME.ID)
                .from(GAME)
                .fetch(0, long.class);

        List<Map<String, Object>> gameListOut = new ArrayList<>();
        for (long gameId : dbGame) {
            gameListOut.add(new Game(gameId).getData());
        }
        ResponseUtilities.asSuccessResponse(event, gameListOut);
    }
}
