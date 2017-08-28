package com.diluv.api.utils

import com.diluv.api.models.Tables.USER
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getUserByUserId(userId: Long, authorized: Boolean): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val user = transaction.select(USER.USERNAME, USER.EMAIL, USER.AVATAR, USER.CREATED_AT, USER.PERMISSION)
            .from(USER)
            .where(USER.ID.eq(userId))
            .fetchOne()

    if (user != null) {
        var userOut = mapOf<String, Any>(
                "username" to user.get(USER.USERNAME),
                "avatar" to user.get(USER.AVATAR),
                "createdAt" to user.get(USER.CREATED_AT),
                "projects" to this.getProjectsByUserId(userId)
        )

        if (authorized) {
            userOut += mapOf<String, Any>(
                    "permission" to user.get(USER.PERMISSION),
                    "email" to user.get(USER.EMAIL)
            )
        }
        return userOut
    } else {
        return mapOf()
    }
}

fun Connection.getUserSettingsByUserId(userId: Long): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val user = transaction.select(USER.EMAIL, USER.AVATAR, USER.FIRST_NAME, USER.LAST_NAME, USER.LOCATION, USER.MFA_ENABLED)
            .from(USER)
            .where(USER.ID.eq(userId))
            .fetchOne()

    if (user != null) {
        return mapOf<String, Any>(
                "email" to user.get(USER.EMAIL),
                "avatar" to user.get(USER.AVATAR),
                "firstName" to user.get(USER.FIRST_NAME),
                "lastName" to user.get(USER.LAST_NAME),
                "location" to user.get(USER.LOCATION),
                "mfaEnabled" to user.get(USER.MFA_ENABLED)
        )
    }
    return mapOf()
}