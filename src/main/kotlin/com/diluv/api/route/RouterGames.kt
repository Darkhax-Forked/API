package com.diluv.api.route

import com.diluv.api.models.Tables
import com.diluv.api.models.Tables.GAME
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
        router.get("/games/:gameSlug").handler(getGameBySlug)
        //TODO add endpoint to get projects for a certain game
        router.get("/games/:gameSlug/projectTypes").handler(getProjectTypesByGameSlug)
        router.get("/games/:gameSlug/projectTypes/:projectTypeSlug").handler(getProjectTypeByProjectTypeSlugByGameSlug)
        router.get("/games/:gameSlug/projectTypes/:projectTypeSlug/projects").handler(getProjectsByProjectTypeSlugByGameSlug)
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
            conn.getGameById(it.get(GAME.ID))
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
    val getGameBySlug = Handler<RoutingContext> { event ->
        val gameSlug = event.request().getParam("gameSlug")

        if (gameSlug != null) {
            val gameId = conn.getGameIdBySlug(gameSlug)
            if (gameId != null) {
                val gameObj = conn.getGameById(gameId)
                if (!gameObj.isEmpty())
                    event.asSuccessResponse(gameObj)
                else
                    event.asErrorResponse(404, "Game not found")
            } else {
                event.asErrorResponse(401, "Game not found")
            }
        } else {
            event.asErrorResponse(401, "Slug is needed for this request")
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
    val getProjectTypesByGameSlug = Handler<RoutingContext> { event ->
        val gameSlug = event.request().getParam("gameSlug")

        if (gameSlug != null) {
            val gameId = conn.getGameIdBySlug(gameSlug)
            if (gameId != null) {
                event.asSuccessResponse(conn.getProjectTypesByGameId(gameId))
            } else {
                event.asErrorResponse(401, "Game not found")
            }
        } else {
            event.asErrorResponse(401, "A slug is needed")
        }
    }

    val getProjectTypeByProjectTypeSlugByGameSlug = Handler<RoutingContext> { event ->
        val gameSlug = event.request().getParam("gameSlug")
        val projectTypeSlug = event.request().getParam("projectTypeSlug")

        if (gameSlug != null && projectTypeSlug != null) {
            val projectTypeId = conn.getProjectTypeIdBySlug(projectTypeSlug)
            if (projectTypeId != null) {
                event.asSuccessResponse(conn.getProjectTypeById(projectTypeId))
            } else {
                event.asErrorResponse(401, "Project type not found")
            }
        } else {
            event.asErrorResponse(401, "A slug is needed")
        }
    }


    val getProjectsByProjectTypeSlugByGameSlug = Handler<RoutingContext> { event ->
        val gameSlug = event.request().getParam("gameSlug")
        val projectTypeSlug = event.request().getParam("projectTypeSlug")

        if (gameSlug != null && projectTypeSlug != null) {
            val projectTypeId = conn.getProjectTypeIdBySlug(projectTypeSlug)
            if (projectTypeId != null) {

                val req = event.request()
                val transaction = DSL.using(conn, SQLDialect.MYSQL)

                val inputPerPage = req.getParam("perPage")

                val dbProjectCount = transaction.select(DSL.count())
                        .from(Tables.PROJECT)
                        .where(Tables.PROJECT.PROJECTTYPEID.eq(projectTypeId))
                        .fetchOne(0, Int::class.java)

                val inputPage = req.getParam("page")
                val inputOrder = req.getParam("order")
                val inputOrderBy = req.getParam("orderBy")

                getPageDetails(inputPage, inputPerPage, dbProjectCount, { page, offset, perPage, totalPageCount ->
                    val dbProject = transaction.select(Tables.PROJECT.ID)
                            .from(Tables.PROJECT)
                            .where(Tables.PROJECT.PROJECTTYPEID.eq(projectTypeId))
                            .orderBy(Tables.PROJECT.CREATEDAT.desc())
                            .limit(offset, perPage)
                            .fetch()

                    val gameListOut = dbProject.map {
                        conn.getProjectById(it.get(Tables.PROJECT.ID))
                    }

                    val ouputData = mapOf(
                            "data" to gameListOut,
                            "page" to page,
                            "order" to "desc",
                            "orderBy" to "newest",
                            "perPage" to perPage,
                            "totalPageCount" to totalPageCount
                    )
                    event.asSuccessResponse(ouputData)
                })
            } else {
                event.asErrorResponse(401, "Project type not found")
            }
        } else {
            event.asErrorResponse(401, "A slug is needed")
        }
    }
}
