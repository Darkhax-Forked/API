package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun getGameById(conn: Connection, gameId: Long): Map<String, Any> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    val gameOut = transaction.select(GAME.ID, GAME.NAME, GAME.WEBSITE, GAME.DESCRIPTION)
            .from(GAME)
            .where(GAME.ID.eq(gameId))
            .fetchOne()
    if (gameOut != null) {
        return mapOf(
                "id" to gameOut.get(GAME.ID),
                "name" to gameOut.get(GAME.NAME),
                "website" to gameOut.get(GAME.WEBSITE),
                "description" to gameOut.get(GAME.DESCRIPTION),
                "versions" to getGameVersionsByGameId(conn, gameId),
                "projectTypes" to getProjectTypesByGameId(conn, gameId)
        )
    } else {
        return mapOf()
    }
}

fun getGameVersionsByGameId(conn: Connection, gameId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    val gameOut = transaction.select(GAMEVERSION.VERSION, GAMEVERSION.WEBSITE, GAMEVERSION.CREATEDAT)
            .from(GAMEVERSION)
            .where(GAMEVERSION.GAMEID.eq(gameId))
            .fetch()
    val gameVersionListOut = gameOut.map {
        mapOf(
                "version" to it.get(GAMEVERSION.VERSION),
                "website" to it.get(GAMEVERSION.WEBSITE),
                "createdAt" to it.get(GAMEVERSION.CREATEDAT)
        )
    }

    if (gameVersionListOut != null) {
        return gameVersionListOut
    } else {
        return listOf()
    }
}

fun getProjectTypesByGameId(conn: Connection, gameId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    val dbProjectType = transaction.select(PROJECTTYPE.ID)
            .from(PROJECTTYPE)
            .where(PROJECTTYPE.GAMEID.eq(gameId))
            .fetch()

    val projectTypeListOut = dbProjectType.map {
        getProjectTypeById(conn, it.get(PROJECTTYPE.ID))
    }

    if (projectTypeListOut != null) {
        return projectTypeListOut
    } else {
        return listOf()
    }
}
