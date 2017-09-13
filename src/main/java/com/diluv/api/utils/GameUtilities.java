package com.diluv.api.utils;

import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class GameUtilities {

    public static Map<String, Object> getGameById(Connection conn, long gameId) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        Record5<Long, String, String, String, String> gameOut = transaction.select(GAME.ID, GAME.NAME, GAME.WEBSITE, GAME.DESCRIPTION, GAME.SLUG)
                .from(GAME)
                .where(GAME.ID.eq(gameId))
                .fetchOne();

        Map<String, Object> out = new HashMap<>();
        if (gameOut != null) {
            out.put("name", gameOut.get(GAME.NAME));
            out.put("website", gameOut.get(GAME.WEBSITE));
            out.put("description", gameOut.get(GAME.DESCRIPTION));
            out.put("slug", gameOut.get(GAME.SLUG));
            out.put("versions", GameUtilities.getGameVersionsByGameId(conn, gameId));
            out.put("projectTypes", GameUtilities.getProjectTypesByGameId(conn, gameId));
        }
        return out;
    }

    public static List<Map<String, Object>> getGameVersionsByGameId(Connection conn, long gameId) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        Result<Record3<String, String, Timestamp>> gameOut = transaction.select(GAME_VERSION.VERSION, GAME_VERSION.WEBSITE, GAME_VERSION.CREATED_AT)
                .from(GAME_VERSION)
                .where(GAME_VERSION.GAME_ID.eq(gameId))
                .fetch();

        List<Map<String, Object>> gameVersionListOut = new ArrayList<>();

        for (Record3<String, String, Timestamp> it : gameOut) {
            Map<String, Object> game = new HashMap<>();

            game.put("version", it.get(GAME_VERSION.VERSION));
            game.put("website", it.get(GAME_VERSION.WEBSITE));
            game.put("createdAt", it.get(GAME_VERSION.CREATED_AT));
            gameVersionListOut.add(game);
        }

        return gameVersionListOut;
    }

    public static List<Map<String, Object>> getProjectTypesByGameId(Connection conn, long gameId) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        List<Long> dbProjectType = transaction.select(PROJECT_TYPE.ID)
                .from(PROJECT_TYPE)
                .where(PROJECT_TYPE.GAME_ID.eq(gameId))
                .fetch(0, long.class);


        List<Map<String, Object>> projectTypeListOut = new ArrayList<>();
        for (long id : dbProjectType) {
            projectTypeListOut.add(ProjectTypeUtilities.getProjectTypeById(conn, id));
        }

        return projectTypeListOut;
    }

    public static Long getGameIdBySlug(Connection conn, String gameSlug) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        return transaction.select(GAME.ID)
                .from(GAME)
                .where(GAME.SLUG.eq(gameSlug))
                .fetchOne(0, long.class);
    }
}