package com.diluv.api.route;

import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.utils.AuthorizationUtilities;
import com.diluv.api.utils.GameUtilities;
import com.diluv.api.utils.ProjectTypeUtilities;
import com.diluv.api.utils.ResponseUtilities;
import com.diluv.api.utils.page.Page;
import com.diluv.api.utils.page.PagesUtilities;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class RouterGames {

    private final Connection conn;

    public RouterGames(Connection conn) {
        this.conn = conn;
    }

    public void createRouter(Router router) {
        router.get("/games").handler(this::getGames);
        router.get("/games/:gameSlug").handler(this::getGameBySlug);
        router.get("/games/:gameSlug/:projectTypeSlug").handler(this::getProjectTypesByGameSlug);
        router.get("/games/:gameSlug/:projectTypeSlug").handler(this::getProjectTypeByProjectTypeSlugByGameSlug);
        router.get("/games/:gameSlug/:projectTypeSlug/projects").handler(this::getProjectsByProjectTypeSlugByGameSlug);
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


    /**
     * @api {get} /games/:id
     * @apiName GetGame
     * @apiVersion 0.1.0
     * @apiGroup Game
     * @apiUse DefaultReturns
     * @apiParam {Integer} id The unique id of the game.
     * @apiSuccess {String} data.id The unique id of the game.
     * @apiSuccess {String} data.name The name of the game.
     * @apiSuccess {String} data.website The link to the game website.
     * @apiSuccess {String} data.description A description of the game.
     */
    public void getGameBySlug(RoutingContext event) {
        String gameSlug = event.request().getParam("gameSlug");

        if (gameSlug != null) {
            Long gameId = GameUtilities.getGameIdBySlug(conn, gameSlug);
            if (gameId != null) {
                Map<String, Object> gameObj = GameUtilities.getGameById(conn, gameId);
                if (!gameObj.isEmpty())
                    ResponseUtilities.asSuccessResponse(event, gameObj);
                else
                    ResponseUtilities.asErrorResponse(event, Errors.NOT_FOUND, "Game not found");
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Game not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Slug is needed for this request");
        }
    }


    /**
     * @api {get} /games/:id/projectTypes
     * @apiName GetGame
     * @apiVersion 0.1.0
     * @apiGroup Game
     * @apiUse DefaultReturns
     * @apiSuccess {String} data.id The unique id of the project type.
     * @apiSuccess {String} data.name The name of the project type.
     * @apiSuccess {String} data.description A description of the project type.
     */
    public void getProjectTypesByGameSlug(RoutingContext event) {
        String gameSlug = event.request().getParam("gameSlug");

        if (gameSlug != null) {
            Long gameId = GameUtilities.getGameIdBySlug(this.conn, gameSlug);
            if (gameId != null) {
                ResponseUtilities.asSuccessResponse(event, GameUtilities.getProjectTypesByGameId(this.conn, gameId));
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Game not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "A slug is needed");
        }
    }

    public void getProjectTypeByProjectTypeSlugByGameSlug(RoutingContext event) {
        String gameSlug = event.request().getParam("gameSlug");
        String projectTypeSlug = event.request().getParam("projectTypeSlug");

        if (gameSlug != null && projectTypeSlug != null) {
            Long projectTypeId = ProjectTypeUtilities.getProjectTypeIdBySlug(this.conn, projectTypeSlug);
            if (projectTypeId != null) {
                Map<String, Object> projectType = ProjectTypeUtilities.getProjectTypeById(this.conn, projectTypeId);
                if (!projectType.isEmpty()) {

                    String token = AuthorizationUtilities.getAuthorizationToken(event);
                    if (token != null) {
                        if (JWT.isTokenValid(this.conn, token)) {
                            JWT jwt = new JWT(token);
                            if (!jwt.isExpired()) {
                                DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
                                Long userId = jwt.getData().getLong("userId");

                                Integer permission = transaction.select(USER.PERMISSION)
                                        .from(USER)
                                        .where(USER.ID.eq(userId))
                                        .fetchOne(0, int.class);

                                if (permission != null)
                                    projectType.put("userPermissions", permission);
                            }
                        }
                    }
                }
                ResponseUtilities.asSuccessResponse(event, projectType);
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project type not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "A slug is needed");
        }
    }

    public void getProjectsByProjectTypeSlugByGameSlug(RoutingContext event) {
        String gameSlug = event.request().getParam("gameSlug");
        String projectTypeSlug = event.request().getParam("projectTypeSlug");

        if (gameSlug != null && projectTypeSlug != null) {
            Long projectTypeId = ProjectTypeUtilities.getProjectTypeIdBySlug(this.conn, projectTypeSlug);
            if (projectTypeId != null) {

                HttpServerRequest req = event.request();
                DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

                String inputPerPage = req.getParam("perPage");

                Integer dbProjectCount = transaction.select(DSL.count())
                        .from(PROJECT)
                        .where(PROJECT.PROJECT_TYPE_ID.eq(projectTypeId))
                        .fetchOne(0, int.class);

                String inputPage = req.getParam("page");
                String inputOrder = req.getParam("order");
                String inputOrderBy = req.getParam("orderBy");

                Page page = PagesUtilities.getPageDetails(inputPage, inputPerPage, dbProjectCount);
                List<Long> dbProject = transaction.select(PROJECT.ID)
                        .from(PROJECT)
                        .where(PROJECT.PROJECT_TYPE_ID.eq(projectTypeId))
                        .orderBy(PROJECT.CREATED_AT.desc())
                        .limit(page.getOffset(), page.getPerPage())
                        .fetch(0, long.class);


                List<Map<String, Object>> gameListOut = new ArrayList<>();
                for (long projectId : dbProject) {
                    gameListOut.add(GameUtilities.getGameById(this.conn, projectId));
                }

                Map<String, Object> outputData = new HashMap<>();
                outputData.put("data", gameListOut);
                outputData.put("page", page);
                outputData.put("order", "desc");
                outputData.put("orderBy", "newest");
                outputData.put("perPage", page.getPerPage());
                outputData.put("totalPageCount", page.getTotalPageCount());

                ResponseUtilities.asSuccessResponse(event, outputData);
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project type not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "A slug is needed");
        }
    }
}
