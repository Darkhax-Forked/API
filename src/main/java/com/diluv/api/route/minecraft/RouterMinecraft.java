package com.diluv.api.route.minecraft;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import com.diluv.api.route.minecraft.projects.RouterMods;
import com.diluv.api.utils.GameUtilities;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;

import java.sql.Connection;
import java.util.Map;

public class RouterMinecraft extends RouterImpl {
    private final Connection conn;
    private final Vertx vertx;
    private final Long gameId;

    public RouterMinecraft(Connection conn, Vertx vertx) {
        super(vertx);
        this.conn = conn;
        this.vertx = vertx;
        this.gameId = GameUtilities.getGameIdBySlug(this.conn, "minecraft");

        this.get("/").handler(this::getMinecraft);
        this.get("/projectTypes").handler(this::getProjectTypesByMinecraft);

        this.mountSubRouter("/mods", new RouterMods(conn, vertx));
    }

    public void getMinecraft(RoutingContext event) {
        if (this.gameId != null) {
            Map<String, Object> gameObj = GameUtilities.getGameById(this.conn, this.gameId);
            if (!gameObj.isEmpty())
                ResponseUtilities.asSuccessResponse(event, gameObj);
            else
                ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

    public void getProjectTypesByMinecraft(RoutingContext event) {

        if (this.gameId != null) {
            ResponseUtilities.asSuccessResponse(event, GameUtilities.getProjectTypesByGameId(this.conn, this.gameId));
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }
}
