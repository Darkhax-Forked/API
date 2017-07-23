package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getCategoriesByProjectTypeId(projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECTTYPECATEGORY.ID, PROJECTTYPECATEGORY.NAME, PROJECTTYPECATEGORY.DESCRIPTION)
            .from(PROJECTTYPECATEGORY)
            .where(PROJECTTYPECATEGORY.PROJECTTYPEID.eq(projectTypeId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            mapOf(
                    "name" to it.get(PROJECTTYPECATEGORY.NAME),
                    "description" to it.get(PROJECTTYPECATEGORY.DESCRIPTION),
                    "slug" to it.get(PROJECTTYPECATEGORY.SLUG)
            )
        }
    }
    return listOf()
}

fun Connection.getProjectsByProjectTypeId(projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID)
            .from(PROJECT)
            .where(PROJECT.PROJECTTYPEID.eq(projectTypeId))
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
    val dbUserProject = transaction.select(PROJECTTYPE.ID, PROJECTTYPE.NAME, PROJECTTYPE.DESCRIPTION, PROJECTTYPE.SLUG)
            .from(PROJECTTYPE)
            .where(PROJECTTYPE.ID.eq(projectTypeId))
            .fetchOne()

    if (dbUserProject != null) {
        return mapOf(
                "name" to dbUserProject.get(PROJECTTYPE.NAME),
                "description" to dbUserProject.get(PROJECTTYPE.DESCRIPTION),
                "slug" to dbUserProject.get(PROJECTTYPE.SLUG),
                "categories" to this.getCategoriesByProjectTypeId(dbUserProject.get(PROJECTTYPE.ID))
        )
    }
    return mapOf()
}

fun Connection.getProjectTypeIdBySlug(projectTypeSlug: String): Long? {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbGame = transaction.select(PROJECTTYPE.ID)
            .from(PROJECTTYPE)
            .where(PROJECTTYPE.SLUG.eq(projectTypeSlug))
            .fetchOne()
    if (dbGame != null)
        return dbGame.get(PROJECTTYPE.ID)
    return null
}