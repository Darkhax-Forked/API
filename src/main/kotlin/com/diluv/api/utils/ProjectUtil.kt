package com.diluv.api.utils

import com.diluv.api.jwt.JWT
import com.diluv.api.models.Tables.*
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

fun Connection.getProjectById(projectId: Long, token: String? = null): Map<String, Any> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    val dbUserProject = transaction.select(PROJECT.ID, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORTDESCRIPTION, PROJECT.USERID, PROJECT.SLUG, PROJECT.LOGO, PROJECT.TOTALDOWNLOADS, PROJECT.CREATEDAT, PROJECT.UPDATEDAT)
            .from(PROJECT)
            .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId)))
            .fetchOne()

    if (dbUserProject != null) {
//        val gameVersions = getProjectGameVersionsById(conn, projectId)

        var data = mapOf(
                "name" to dbUserProject.get(PROJECT.NAME),
                "description" to dbUserProject.get(PROJECT.DESCRIPTION),
                "shortDescription" to dbUserProject.get(PROJECT.SHORTDESCRIPTION),
                "slug" to dbUserProject.get(PROJECT.SLUG),
                "logo" to dbUserProject.get(PROJECT.LOGO),
                "totalDownloads" to dbUserProject.get(PROJECT.TOTALDOWNLOADS),
                //                "projectTypeId" to dbUserProject[ModelProject.projectTypeId],
                "authors" to getProjectMembersByProjectId(projectId),
                //                "gameVersions" to gameVersions,
                "categories" to getProjectCategoriesById(projectId),
                "createdAt" to dbUserProject.get(PROJECT.CREATEDAT),
                "updatedAt" to dbUserProject.get(PROJECT.UPDATEDAT)
        )
        if (token != null) {
            val userId = JWT(token.toString()).data.getLong("userId")
            if (dbUserProject.get(PROJECT.USERID) == userId)
                data += mapOf(
                        "permission" to 10000000000L
                )
        }

        return data
    }
    return mapOf()
}

fun Connection.getProjectMembersByProjectId(projectId: Long): List<Map<String, Any>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

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

fun Connection.getProjectFileGameVersionsById(projectFileId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val dbProjectGameVersion = transaction.select(GAMEVERSION.VERSION, GAMEVERSION.CREATEDAT)
            .from(PROJECTFILEGAMEVERSION.join(GAMEVERSION).on(PROJECTFILEGAMEVERSION.PROJECTVERSIONID.eq(GAMEVERSION.ID)))
            .where(PROJECTFILEGAMEVERSION.PROJECTFILEID.eq(projectFileId))
            .fetch()

    return dbProjectGameVersion.map {
        mapOf(
                "version" to it.get(GAMEVERSION.VERSION),
                "createdAt" to it.get(GAMEVERSION.CREATEDAT)
        )
    }
}

fun Connection.getProjectCategoriesById(projectId: Long): List<Map<String, Any?>> {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
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

fun Connection.getProjectFilesById(projectId: Long): List<Map<String, Any>> {
    //TODO Remove status for unauth requests
    val transaction = DSL.using(this, SQLDialect.MYSQL)

    val fileURL = System.getenv("fileURL")
    val dbProjectFiles = transaction.select(PROJECTFILE.ID, PROJECTFILE.SHA256, PROJECTFILE.FILENAME, PROJECTFILE.SIZE, PROJECTFILE.RELEASETYPE, PROJECTFILE.DISPLAYNAME, PROJECTFILE.DOWNLOADS, PROJECTFILE.CREATEDAT, PROJECTFILE.PARENTID, PROJECTFILE.STATUS)
            .from(PROJECTFILE)
            .where(PROJECTFILE.ID.eq(projectId))
            .fetch()

    return dbProjectFiles.map {
        mapOf(
                "sha256" to it.get(PROJECTFILE.SHA256),
                "fileName" to it.get(PROJECTFILE.FILENAME),
                "size" to it.get(PROJECTFILE.SIZE),
                "releaseType" to it.get(PROJECTFILE.RELEASETYPE),
                "displayName" to it.get(PROJECTFILE.DISPLAYNAME),
                "downloads" to it.get(PROJECTFILE.DOWNLOADS),
                "createdAt" to it.get(PROJECTFILE.CREATEDAT),
                "parentId" to it.get(PROJECTFILE.PARENTID),
                "status" to it.get(PROJECTFILE.STATUS),
                "gameVersions" to this.getProjectFileGameVersionsById(it.get(PROJECTFILE.ID)),
                "downloadUrl" to fileURL + "/" + it.get(PROJECTFILE.SHA256) + "/" + it.get(PROJECTFILE.DISPLAYNAME)
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
        val dbProjectFile = transaction.insertInto(PROJECTFILE, PROJECTFILE.SHA256, PROJECTFILE.FILENAME, PROJECTFILE.DISPLAYNAME, PROJECTFILE.SIZE, PROJECTFILE.RELEASETYPE, PROJECTFILE.STATUS, PROJECTFILE.PARENTID, PROJECTFILE.PROJECTID, PROJECTFILE.USERID)
                .values(sha256, fileName, displayName, size, releaseType, status, parentId, dbProject.get(PROJECT.ID), userId)
                .returning(PROJECTFILE.ID)
                .fetchOne()
        if (dbProjectFile != null)
            return dbProjectFile.get(PROJECTFILE.ID)
        else
            return null
    }
    return null
}

fun Connection.insertProject(name: String, description: String, shortDescription: String, slug: String, logo: String, projectTypeId: Long, userId: Long) {
    val transaction = DSL.using(this, SQLDialect.MYSQL)
    transaction.insertInto(PROJECT, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORTDESCRIPTION, PROJECT.SLUG, PROJECT.LOGO, PROJECT.PROJECTTYPEID, PROJECT.USERID)
            .values(name, description, shortDescription, slug, logo, projectTypeId, userId)
            .execute()
}