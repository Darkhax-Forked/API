package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getCategoriesByProjectTypeId(projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT_TYPE_CATEGORY.ID, PROJECT_TYPE_CATEGORY.NAME, PROJECT_TYPE_CATEGORY.DESCRIPTION)
            .from(PROJECT_TYPE_CATEGORY)
            .where(PROJECT_TYPE_CATEGORY.PROJECT_TYPE_ID.eq(projectTypeId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            mapOf(
                    "name" to it.get(PROJECT_TYPE_CATEGORY.NAME),
                    "description" to it.get(PROJECT_TYPE_CATEGORY.DESCRIPTION),
                    "slug" to it.get(PROJECT_TYPE_CATEGORY.SLUG)
            )
        }
    }
    return listOf()
}

fun Connection.getProjectsByProjectTypeId(projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID)
            .from(PROJECT)
            .where(PROJECT.PROJECT_TYPE_ID.eq(projectTypeId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            this.getProjectById(it.get(PROJECT.ID))
        }
    }
    return listOf()
}

fun Connection.getProjectTypeById(projectTypeId: Long): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbUserProject = transaction.select(PROJECT_TYPE.ID, PROJECT_TYPE.NAME, PROJECT_TYPE.DESCRIPTION, PROJECT_TYPE.SLUG)
            .from(PROJECT_TYPE)
            .where(PROJECT_TYPE.ID.eq(projectTypeId))
            .fetchOne()

    if (dbUserProject != null) {
        val dbPermission = transaction.select(PROJECT_TYPE_PERMISSION.PERMISSION_CREATE)
                .from(PROJECT_TYPE_PERMISSION)
                .where(PROJECT_TYPE_PERMISSION.ID.eq(projectTypeId))
                .fetchOne()

        return mapOf(
                "name" to dbUserProject.get(PROJECT_TYPE.NAME),
                "description" to dbUserProject.get(PROJECT_TYPE.DESCRIPTION),
                "slug" to dbUserProject.get(PROJECT_TYPE.SLUG),
                "categories" to this.getCategoriesByProjectTypeId(dbUserProject.get(PROJECT_TYPE.ID)),
                "permission" to dbPermission.get(PROJECT_TYPE_PERMISSION.PERMISSION_CREATE)
        )
    }
    return mapOf()
}

fun Connection.getProjectTypeIdBySlug(projectTypeSlug: String): Long? {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbGame = transaction.select(PROJECT_TYPE.ID)
            .from(PROJECT_TYPE)
            .where(PROJECT_TYPE.SLUG.eq(projectTypeSlug))
            .fetchOne()
    if (dbGame != null)
        return dbGame.get(PROJECT_TYPE.ID)
    return null
}