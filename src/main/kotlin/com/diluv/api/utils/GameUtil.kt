package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getGameById(gameId: Long): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val gameOut = transaction.select(GAME.ID, GAME.NAME, GAME.WEBSITE, GAME.DESCRIPTION, GAME.SLUG)
            .from(GAME)
            .where(GAME.ID.eq(gameId))
            .fetchOne()
    if (gameOut != null) {
        return mapOf(
                "name" to gameOut.get(GAME.NAME),
                "website" to gameOut.get(GAME.WEBSITE),
                "description" to gameOut.get(GAME.DESCRIPTION),
                "slug" to gameOut.get(GAME.SLUG),
                "versions" to this.getGameVersionsByGameId(gameId),
                "projectTypes" to this.getProjectTypesByGameId(gameId)
        )
    } else {
        return mapOf()
    }
}

fun Connection.getGameVersionsByGameId(gameId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val gameOut = transaction.select(GAME_VERSION.VERSION, GAME_VERSION.WEBSITE, GAME_VERSION.CREATED_AT)
            .from(GAME_VERSION)
            .where(GAME_VERSION.GAME_ID.eq(gameId))
            .fetch()
    val gameVersionListOut = gameOut.map {
        mapOf(
                "version" to it.get(GAME_VERSION.VERSION),
                "website" to it.get(GAME_VERSION.WEBSITE),
                "createdAt" to it.get(GAME_VERSION.CREATED_AT)
        )
    }

    if (gameVersionListOut != null) {
        return gameVersionListOut
    } else {
        return listOf()
    }
}

fun Connection.getProjectTypesByGameId(gameId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbProjectType = transaction.select(PROJECT_TYPE.ID)
            .from(PROJECT_TYPE)
            .where(PROJECT_TYPE.GAME_ID.eq(gameId))
            .fetch()

    val projectTypeListOut = dbProjectType.map {
        this.getProjectTypeById(it.get(PROJECT_TYPE.ID))
    }

    if (projectTypeListOut != null) {
        return projectTypeListOut
    } else {
        return listOf()
    }
}

fun Connection.getGameIdBySlug(gameSlug: String): Long? {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbGame = transaction.select(GAME.ID)
            .from(GAME)
            .where(GAME.SLUG.eq(gameSlug))
            .fetchOne()
    if (dbGame != null)
        return dbGame.get(GAME.ID)
    return null
}