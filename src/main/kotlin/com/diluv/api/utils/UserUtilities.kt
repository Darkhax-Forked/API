package com.diluv.api.utils

import com.diluv.api.models.Tables
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection


fun Connection.getUserByUserId(userId: Long, authorized: Boolean): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val user = transaction.select(Tables.USER.USERNAME, Tables.USER.EMAIL, Tables.USER.AVATAR, Tables.USER.CREATED_AT, Tables.USER.PERMISSION)
            .from(Tables.USER)
            .where(Tables.USER.ID.eq(userId))
            .fetchOne()

    if (user != null) {
        var userOut = mapOf<String, Any>(
                "username" to user.get(Tables.USER.USERNAME),
                "avatar" to user.get(Tables.USER.AVATAR),
                "createdAt" to user.get(Tables.USER.CREATED_AT),
                "projects" to this.getProjectsByUserId(userId)
        )

        if (authorized) {
            userOut += mapOf<String, Any>(
                    "permission" to user.get(Tables.USER.PERMISSION),
                    "email" to user.get(Tables.USER.EMAIL)
            )
        }
        return userOut
    } else {
        return mapOf()
    }
}