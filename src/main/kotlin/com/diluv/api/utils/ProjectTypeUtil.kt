package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun getCategoriesByProjectTypeId(conn: Connection, projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECTTYPECATEGORY.ID, PROJECTTYPECATEGORY.NAME, PROJECTTYPECATEGORY.DESCRIPTION)
            .from(PROJECTTYPECATEGORY)
            .where(PROJECTTYPECATEGORY.PROJECTTYPEID.eq(projectTypeId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            mapOf(
                    "id" to it.get(PROJECTTYPECATEGORY.ID),
                    "name" to it.get(PROJECTTYPECATEGORY.NAME),
                    "description" to it.get(PROJECTTYPECATEGORY.DESCRIPTION),
                    "slug" to "/test"
            )
        }
    }
    return listOf()
}

fun getProjectsByProjectTypeId(conn: Connection, projectTypeId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID)
            .from(PROJECT)
            .where(PROJECT.PROJECTTYPEID.eq(projectTypeId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            getProjectById(conn, it.get(PROJECT.ID))
        }
    }
    return listOf()
}

fun getProjectTypeById(conn: Connection, projectTypeId: Long): Map<String, Any> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECTTYPE.ID, PROJECTTYPE.NAME, PROJECTTYPE.DESCRIPTION, PROJECTTYPE.SLUG)
            .from(PROJECTTYPE)
            .where(PROJECTTYPE.ID.eq(projectTypeId))
            .fetchOne()

    if (dbUserProject != null) {
        return mapOf(
                "id" to dbUserProject.get(PROJECTTYPE.ID),
                "name" to dbUserProject.get(PROJECTTYPE.NAME),
                "description" to dbUserProject.get(PROJECTTYPE.DESCRIPTION),
                "slug" to dbUserProject.get(PROJECTTYPE.SLUG),
                "categories" to getCategoriesByProjectTypeId(conn, dbUserProject.get(PROJECTTYPE.ID))
        )
    }
    return mapOf()
}