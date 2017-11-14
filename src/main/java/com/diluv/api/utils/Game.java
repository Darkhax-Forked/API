package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import com.diluv.api.models.tables.records.GameRecord;
import org.jooq.Record3;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class Game {

    private final Long gameId;

    public Game(String gameSlug) {
        this.gameId = this.getGameIdBySlug(gameSlug);
    }

    public Game(long gameId) {
        this.gameId = gameId;
    }

    /**
     * Returns information about the game via the game slug
     * Included information is name, website, description, slug, version, project types
     *
     * @return A map of data that the game holds,
     */
    public Map<String, Object> getData() {
        GameRecord gameOut = DiluvAPI.getDSLContext()
                .selectFrom(GAME)
                .where(GAME.ID.eq(gameId))
                .fetchOne();

        Map<String, Object> out = new HashMap<>();
        if (gameOut != null) {
            out.put("name", gameOut.get(GAME.NAME));
            out.put("website", gameOut.get(GAME.WEBSITE));
            out.put("description", gameOut.get(GAME.DESCRIPTION));
            out.put("slug", gameOut.get(GAME.SLUG));
            out.put("versions", this.getGameVersions());
            out.put("projectTypes", this.getProjectTypes());
        }
        return out;
    }

    public List<Map<String, Object>> getGameVersions() {
        Result<Record3<String, String, Timestamp>> gameOut = DiluvAPI.getDSLContext().select(GAME_VERSION.VERSION, GAME_VERSION.WEBSITE, GAME_VERSION.CREATED_AT)
                .from(GAME_VERSION)
                .where(GAME_VERSION.GAME_ID.eq(this.gameId))
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

    public List<Map<String, Object>> getProjectTypes() {
        List<Long> projectTypeList = DiluvAPI.getDSLContext().select(PROJECT_TYPE.ID)
                .from(PROJECT_TYPE)
                .where(PROJECT_TYPE.GAME_ID.eq(this.gameId))
                .fetch(0, long.class);
        List<Map<String, Object>> projectTypeListOut = new ArrayList<>();
        for (Long projectTypeId : projectTypeList) {
            projectTypeListOut.add(new ProjectType(this, projectTypeId).getData());
        }

        return projectTypeListOut;
    }

    private Long getGameIdBySlug(String gameSlug) {
        return DiluvAPI.getDSLContext().select(GAME.ID)
                .from(GAME)
                .where(GAME.SLUG.eq(gameSlug))
                .fetchOne(0, long.class);
    }

    public Long getGameId() {
        return gameId;
    }
}