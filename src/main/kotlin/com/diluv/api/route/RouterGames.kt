package com.diluv.api.route

import com.diluv.api.models.Tables.GAME
import com.diluv.api.models.Tables.PROJECT
import com.diluv.api.utils.*
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

class RouterGames(val conn: Connection) {

    fun createRouter(router: Router) {
        router.get("/games").handler(getGames)
        router.get("/games/:gameId").handler(getGameById)
        //TODO add endpoint to get projects for a certain game
        router.get("/games/:gameId/projectTypes").handler(getProjectTypesByGameId)
        router.get("/games/:gameId/projectTypes/:projectTypeId").handler(getProjectTypeByProjectTypeIdByGameId)
        router.get("/games/:gameId/projectTypes/:projectTypeId/projects").handler(getProjectsByProjectTypeIdByGameId)
    }

    /**
     * @api {get} /games
     * @apiName GetGameList
     * @apiVersion 0.1.0
     * @apiGroup Game
     *
     * @apiUse DefaultReturns
     *
     * @apiSuccess {Integer} data.id The unique id of the game.
     * @apiSuccess {String} data.name The name of the game.
     * @apiSuccess {String} data.website The link to the game website.
     * @apiSuccess {String} data.description A description of the game.
     */
    val getGames = Handler<RoutingContext> { event ->
        //TODO allow search via name
        val transaction = DSL.using(conn, SQLDialect.MYSQL)

        val dbGame = transaction.select(GAME.ID, GAME.NAME, GAME.WEBSITE, GAME.DESCRIPTION)
                .from(GAME)
                .fetch()

        val gameListOut = dbGame.map {
            getGameById(conn, it.get(PROJECT.ID))
        }
        event.asSuccessResponse(gameListOut)
    }


    /**
     * @api {get} /games/:id
     * @apiName GetGame
     * @apiVersion 0.1.0
     * @apiGroup Game
     *
     * @apiUse DefaultReturns
     * @apiParam {Integer} id The unique id of the game.
     *
     * @apiSuccess {String} data.id The unique id of the game.
     * @apiSuccess {String} data.name The name of the game.
     * @apiSuccess {String} data.website The link to the game website.
     * @apiSuccess {String} data.description A description of the game.
     */
    val getGameById = Handler<RoutingContext> { event ->
        val gameId = event.request().getParam("gameId").toLongOrNull()

        if (gameId != null) {
            val gameObj = getGameById(conn, gameId)
            if (!gameObj.isEmpty())
                event.asSuccessResponse(gameObj)
            else
                event.asErrorResponse(404, "Game not found")
        } else {
            event.asErrorResponse(401, "Id is needed for this request")
        }
    }


    /**
     * @api {get} /games/:id/projectTypes
     * @apiName GetGame
     * @apiVersion 0.1.0
     * @apiGroup Game
     *
     * @apiUse DefaultReturns
     *
     * @apiSuccess {String} data.id The unique id of the project type.
     * @apiSuccess {String} data.name The name of the project type.
     * @apiSuccess {String} data.description A description of the project type.
     */
    val getProjectTypesByGameId = Handler<RoutingContext> { event ->
        val gameId = event.request().getParam("gameId").toLongOrNull()

        if (gameId != null) {
            event.asSuccessResponse(getProjectTypesByGameId(conn, gameId))
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }

    val getProjectTypeByProjectTypeIdByGameId = Handler<RoutingContext> { event ->
        val gameId = event.request().getParam("gameId").toLongOrNull()
        val projectTypeId = event.request().getParam("projectTypeId").toLongOrNull()

        if (gameId != null && projectTypeId != null) {
            event.asSuccessResponse(getProjectTypeById(conn, projectTypeId))
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }


    val getProjectsByProjectTypeIdByGameId = Handler<RoutingContext> { event ->
        val gameId = event.request().getParam("gameId").toLongOrNull()
        val projectTypeId = event.request().getParam("projectTypeId").toLongOrNull()

        if (gameId != null && projectTypeId != null) {
            event.asSuccessResponse(getProjectsByProjectTypeId(conn, projectTypeId))
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }
}
