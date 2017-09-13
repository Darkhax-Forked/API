package com.diluv.api.utils;

import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.diluv.api.models.Tables.USER;

public class UserUtilities {
    public static Map<String, Object> getUserByUserId(Connection conn, long userId, boolean authorized) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        Record5<String, String, String, Timestamp, Integer> user = transaction.select(USER.USERNAME, USER.EMAIL, USER.AVATAR, USER.CREATED_AT, USER.PERMISSION)
                .from(USER)
                .where(USER.ID.eq(userId))
                .fetchOne();

        Map<String, Object> userOut = new HashMap<>();
        if (user != null) {
            userOut.put("username", user.get(USER.USERNAME));
            userOut.put("avatar", user.get(USER.AVATAR));
            userOut.put("createdAt", user.get(USER.CREATED_AT));
            userOut.put("projects", ProjectUtilities.getProjectsByUserId(conn, userId));

            if (authorized) {
                userOut.put("permission", user.get(USER.PERMISSION));
                userOut.put("email", user.get(USER.EMAIL));
            }
        }
        return userOut;
    }

    public static Map<String, Object> getUserSettingsByUserId(Connection conn, long userId) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        Record6<String, String, String, String, String, Boolean> user = transaction.select(USER.EMAIL, USER.AVATAR, USER.FIRST_NAME, USER.LAST_NAME, USER.LOCATION, USER.MFA_ENABLED)
                .from(USER)
                .where(USER.ID.eq(userId))
                .fetchOne();

        Map<String, Object> userSettingsOut = new HashMap<>();

        if (user != null) {
            userSettingsOut.put("email", user.get(USER.EMAIL));
            userSettingsOut.put("avatar", user.get(USER.AVATAR));
            userSettingsOut.put("firstName", user.get(USER.FIRST_NAME));
            userSettingsOut.put("lastName", user.get(USER.LAST_NAME));
            userSettingsOut.put("location", user.get(USER.LOCATION));
            userSettingsOut.put("mfaEnabled", user.get(USER.MFA_ENABLED));
        }
        return userSettingsOut;
    }
}