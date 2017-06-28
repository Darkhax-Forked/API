package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun getProjectMembersByProjectId(conn: Connection, projectId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    //TODO Catch TooManyRowsException
    val dbUserProject = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATEDAT, PROJECT.ID)
            .from(PROJECT.join(USER).on(PROJECT.USERID.eq(USER.ID)))
            .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId))).fetchOne()

    if (dbUserProject != null) {
        val dbUser = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATEDAT, PROJECTMEMBER.ROLE)
                .from(USER.join(PROJECTMEMBER).on(USER.ID.eq(PROJECTMEMBER.USERID)))
                .where(PROJECTMEMBER.PROJECTID.eq(projectId)).fetch()
        return dbUser.map {
            mapOf(
                    "username" to it.get(USER.USERNAME),
                    "avatar" to it.get(USER.AVATAR),
                    "createdAt" to it.get(USER.CREATEDAT),
                    "role" to it.get(PROJECTMEMBER.ROLE)
            )
        } + mapOf(
                "username" to dbUserProject.get(USER.USERNAME),
                "avatar" to dbUserProject.get(USER.AVATAR),
                "createdAt" to dbUserProject.get(USER.CREATEDAT),
                "role" to "Owner"
        )
    }
    return listOf()
}

fun getProjectGameVersionsById(conn: Connection, projectId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    val dbProjectGameVersion = transaction.select(GAMEVERSION.VERSION, GAMEVERSION.CREATEDAT)
            .from(PROJECTGAMEVERSION.join(GAMEVERSION).on(PROJECTGAMEVERSION.PROJECTVERSIONID.eq(GAMEVERSION.ID)))
            .where(PROJECTGAMEVERSION.PROJECTID.eq(projectId))
            .fetch()

    return dbProjectGameVersion.map {
        mapOf(
                "version" to it.get(GAMEVERSION.VERSION),
                "createdAt" to it.get(GAMEVERSION.CREATEDAT)
        )
    }
}

fun getProjectCategoriesById(conn: Connection, projectId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)
    val dbProjectGameVersion = transaction.select(PROJECTTYPECATEGORY.NAME, PROJECTTYPECATEGORY.DESCRIPTION)
            .from(PROJECTCATEGORY.join(PROJECTTYPECATEGORY).on(PROJECTCATEGORY.PROJECTTYPECATEGORYID.eq(PROJECTTYPECATEGORY.ID)))
            .where(PROJECTCATEGORY.PROJECTID.eq(projectId))
            .fetch()

    return dbProjectGameVersion.map {
        mapOf(
                "name" to it.get(PROJECTTYPECATEGORY.NAME),
                "description" to it.get(PROJECTTYPECATEGORY.DESCRIPTION)
        )
    }
}

fun getProjectById(conn: Connection, projectId: Long): Map<String, Any> {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORTDESCRIPTION, PROJECT.SLUG, PROJECT.LOGO, PROJECT.TOTALDOWNLOADS, PROJECT.CREATEDAT, PROJECT.UPDATEDAT)
            .from(PROJECT)
            .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId)))
            .fetchOne()

    if (dbUserProject != null) {
        val userList = getProjectMembersByProjectId(conn, projectId)
        val gameVersions = getProjectGameVersionsById(conn, projectId)
        val categories = getProjectCategoriesById(conn, projectId)
        return mapOf(
                "id" to dbUserProject.get(PROJECT.ID),
                "name" to dbUserProject.get(PROJECT.NAME),
                "description" to dbUserProject.get(PROJECT.DESCRIPTION),
                "shortDescription" to dbUserProject.get(PROJECT.SHORTDESCRIPTION),
                "slug" to dbUserProject.get(PROJECT.SLUG),
                "logo" to dbUserProject.get(PROJECT.LOGO),
                "totalDownloads" to dbUserProject.get(PROJECT.TOTALDOWNLOADS),
                //                    "projectTypeId" to dbUserProject[ModelProject.projectTypeId],
                "authors" to userList,
                "gameVersions" to gameVersions,
                "categories" to categories,
                "createdAt" to dbUserProject.get(PROJECT.CREATEDAT),
                "updatedAt" to dbUserProject.get(PROJECT.UPDATEDAT)
        )
    }
    return mapOf()
}