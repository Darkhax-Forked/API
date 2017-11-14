package com.diluv.api.route.minecraft;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.route.minecraft.projects.RouterMods;
import com.diluv.api.utils.Game;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;

import java.util.Map;

public class RouterMinecraft extends RouterImpl {
    private final Game minecraft;

    public RouterMinecraft(Vertx vertx) {
        super(vertx);
        this.minecraft = new Game("minecraft");

        this.get("/").handler(this::getMinecraft);
        this.get("/projectTypes").handler(this::getProjectTypesByMinecraft);

        this.mountSubRouter("/mods", new RouterMods(this.minecraft, vertx));
    }

    public void getMinecraft(RoutingContext event) {
        Map<String, Object> gameObj = this.minecraft.getData();
        if (!gameObj.isEmpty())
            ResponseUtilities.asSuccessResponse(event, gameObj);
        else
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
    }

    public void getProjectTypesByMinecraft(RoutingContext event) {
        ResponseUtilities.asSuccessResponse(event, this.minecraft.getProjectTypes());
    }
}
