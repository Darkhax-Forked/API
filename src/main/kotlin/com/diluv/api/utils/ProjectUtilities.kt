package com.diluv.api.utils

import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getProjectById(projectId: Long, userId: Long? = null): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORT_DESCRIPTION, PROJECT.USER_ID, PROJECT.SLUG, PROJECT.LOGO, PROJECT.TOTAL_DOWNLOADS, PROJECT.CREATED_AT, PROJECT.UPDATED_AT)
            .from(PROJECT)
            .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId)))
            .fetchOne()

    if (dbUserProject != null) {
//        val gameVersions = getProjectGameVersionsById(conn, projectId)

        var data = mapOf(
                "name" to dbUserProject.get(PROJECT.NAME),
                "description" to dbUserProject.get(PROJECT.DESCRIPTION),
                "shortDescription" to dbUserProject.get(PROJECT.SHORT_DESCRIPTION),
                "slug" to dbUserProject.get(PROJECT.SLUG),
                "logo" to dbUserProject.get(PROJECT.LOGO),
                "totalDownloads" to dbUserProject.get(PROJECT.TOTAL_DOWNLOADS),
                //                "projectTypeId" to dbUserProject[ModelProject.projectTypeId],
                "authors" to getProjectMembersByProjectId(projectId),
                //                "gameVersions" to gameVersions,
                "categories" to getProjectCategoriesById(projectId),
                "createdAt" to dbUserProject.get(PROJECT.CREATED_AT),
                "updatedAt" to dbUserProject.get(PROJECT.UPDATED_AT)
        )
        if (userId != null) {
            if (dbUserProject.get(PROJECT.USER_ID) == userId)
                data += mapOf(
                        "permission" to 0x4
                )
        }

        return data
    }
    return mapOf()
}

fun Connection.getProjectsByUserId(userId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID)
            .from(PROJECT)
            .where(PROJECT.USER_ID.eq(userId))
            .fetch()

    if (dbUserProject != null) {
        return dbUserProject.map {
            this.getProjectById(it.get(PROJECT.ID), userId)
        }
    }
    return listOf()
}

fun Connection.getProjectMembersByProjectId(projectId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    //TODO Catch TooManyRowsException
    val dbUserProject = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT, PROJECT.ID)
            .from(PROJECT.join(USER).on(PROJECT.USER_ID.eq(USER.ID)))
            .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId))).fetchOne()

    if (dbUserProject != null) {
        val dbUser = transaction.select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT, PROJECT_MEMBER.ROLE)
                .from(USER.join(PROJECT_MEMBER).on(USER.ID.eq(PROJECT_MEMBER.USER_ID)))
                .where(PROJECT_MEMBER.PROJECT_ID.eq(projectId)).fetch()
        return dbUser.map {
            mapOf(
                    "username" to it.get(USER.USERNAME),
                    "avatar" to it.get(USER.AVATAR),
                    "createdAt" to it.get(USER.CREATED_AT),
                    "role" to it.get(PROJECT_MEMBER.ROLE)
            )
        } + mapOf(
                "username" to dbUserProject.get(USER.USERNAME),
                "avatar" to dbUserProject.get(USER.AVATAR),
                "createdAt" to dbUserProject.get(USER.CREATED_AT),
                "role" to "Owner"
        )
    }
    return listOf()
}

fun Connection.getProjectFileGameVersionsById(projectFileId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbProjectGameVersion = transaction.select(GAME_VERSION.VERSION, GAME_VERSION.CREATED_AT)
            .from(PROJECT_FILE_GAME_VERSION.join(GAME_VERSION).on(PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID.eq(GAME_VERSION.ID)))
            .where(PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID.eq(projectFileId))
            .fetch()

    return dbProjectGameVersion.map {
        mapOf(
                "version" to it.get(GAME_VERSION.VERSION),
                "createdAt" to it.get(GAME_VERSION.CREATED_AT)
        )
    }
}

fun Connection.getProjectCategoriesById(projectId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbProjectGameVersion = transaction.select(PROJECT_TYPE_CATEGORY.NAME, PROJECT_TYPE_CATEGORY.DESCRIPTION)
            .from(PROJECT_CATEGORY.join(PROJECT_TYPE_CATEGORY).on(PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID.eq(PROJECT_TYPE_CATEGORY.ID)))
            .where(PROJECT_CATEGORY.PROJECT_ID.eq(projectId))
            .fetch()

    return dbProjectGameVersion.map {
        mapOf(
                "name" to it.get(PROJECT_TYPE_CATEGORY.NAME),
                "description" to it.get(PROJECT_TYPE_CATEGORY.DESCRIPTION)
        )
    }
}

fun Connection.getProjectFilesById(projectId: Long): List<Map<String, Any>> {
    //TODO Remove status for unauth requests
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val fileURL = System.getenv("fileURL")
    val dbProjectFiles = transaction.select(PROJECT_FILE.ID, PROJECT_FILE.SHA256, PROJECT_FILE.FILE_NAME, PROJECT_FILE.SIZE, PROJECT_FILE.RELEASE_TYPE, PROJECT_FILE.DISPLAY_NAME, PROJECT_FILE.DOWNLOADS, PROJECT_FILE.CREATED_AT, PROJECT_FILE.PARENT_ID, PROJECT_FILE.STATUS)
            .from(PROJECT_FILE)
            .where(PROJECT_FILE.ID.eq(projectId))
            .fetch()

    return dbProjectFiles.map {
        mapOf(
                "sha256" to it.get(PROJECT_FILE.SHA256),
                "fileName" to it.get(PROJECT_FILE.FILE_NAME),
                "size" to it.get(PROJECT_FILE.SIZE),
                "releaseType" to it.get(PROJECT_FILE.RELEASE_TYPE),
                "displayName" to it.get(PROJECT_FILE.DISPLAY_NAME),
                "downloads" to it.get(PROJECT_FILE.DOWNLOADS),
                "createdAt" to it.get(PROJECT_FILE.CREATED_AT),
                "parentId" to it.get(PROJECT_FILE.PARENT_ID),
                "status" to it.get(PROJECT_FILE.STATUS),
                "gameVersions" to this.getProjectFileGameVersionsById(it.get(PROJECT_FILE.ID)),
                "downloadUrl" to fileURL + "/" + it.get(PROJECT_FILE.ID) + "/" + it.get(PROJECT_FILE.SHA256) + "/" + it.get(PROJECT_FILE.DISPLAY_NAME)
        )
    }
}

fun Connection.getProjectIdBySlug(projectSlug: String): Long? {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbGame = transaction.select(PROJECT.ID)
            .from(PROJECT)
            .where(PROJECT.SLUG.eq(projectSlug))
            .fetchOne()

    if (dbGame != null)
        return dbGame.get(PROJECT.ID)
    return null
}

fun Connection.insertProjectFiles(sha256: String, fileName: String, displayName: String, size: Long, releaseType: String, status: String, parentId: Long?, projectSlug: String, userId: Long): Long? {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbProject = transaction.select(PROJECT.ID, PROJECT.SLUG)
            .from(PROJECT)
            .where(PROJECT.SLUG.eq(projectSlug))
            .fetchOne()
    if (dbProject != null) {
        val dbProjectFile = transaction.insertInto(PROJECT_FILE, PROJECT_FILE.SHA256, PROJECT_FILE.FILE_NAME, PROJECT_FILE.DISPLAY_NAME, PROJECT_FILE.SIZE, PROJECT_FILE.RELEASE_TYPE, PROJECT_FILE.STATUS, PROJECT_FILE.PARENT_ID, PROJECT_FILE.PROJECT_ID, PROJECT_FILE.USER_ID)
                .values(sha256, fileName, displayName, size, releaseType, status, parentId, dbProject.get(PROJECT.ID), userId)
                .returning(PROJECT_FILE.ID)
                .fetchOne()
        if (dbProjectFile != null)
            return dbProjectFile.get(PROJECT_FILE.ID)
    }
    return null
}

fun Connection.insertProject(name: String, description: String, shortDescription: String, slug: String, logo: String, projectTypeId: Long, userId: Long) {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    transaction.insertInto(PROJECT, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORT_DESCRIPTION, PROJECT.SLUG, PROJECT.LOGO, PROJECT.PROJECT_TYPE_ID, PROJECT.USER_ID)
            .values(name, description, shortDescription, slug, logo, projectTypeId, userId)
            .execute()
}