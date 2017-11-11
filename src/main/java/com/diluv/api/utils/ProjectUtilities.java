package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import org.jooq.Record10;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class ProjectUtilities {
    public static Map<String, Object> getProjectById(long projectId, Long userId) {
        Record10<Long, String, String, String, Long, String, String, Long, Timestamp, Timestamp> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT.ID, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORT_DESCRIPTION, PROJECT.USER_ID, PROJECT.SLUG, PROJECT.LOGO, PROJECT.TOTAL_DOWNLOADS, PROJECT.CREATED_AT, PROJECT.UPDATED_AT)
                .from(PROJECT)
                .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId)))
                .fetchOne();

        Map<String, Object> projectOut = new HashMap<>();
        if (dbUserProject != null) {
            //        val gameVersions = getProjectGameVersionsById(conn, projectId)

            projectOut.put("name", dbUserProject.get(PROJECT.NAME));
            projectOut.put("description", dbUserProject.get(PROJECT.DESCRIPTION));
            projectOut.put("shortDescription", dbUserProject.get(PROJECT.SHORT_DESCRIPTION));
            projectOut.put("slug", dbUserProject.get(PROJECT.SLUG));
            projectOut.put("logo", dbUserProject.get(PROJECT.LOGO));
            projectOut.put("totalDownloads", dbUserProject.get(PROJECT.TOTAL_DOWNLOADS));
            //                "projectTypeId", dbUserProject[ModelProject.projectTypeId],
            projectOut.put("authors", ProjectUtilities.getProjectMembersByProjectId(projectId));
            //                "gameVersions", gameVersions,
            projectOut.put("categories", ProjectUtilities.getProjectCategoriesById(projectId));
            projectOut.put("createdAt", dbUserProject.get(PROJECT.CREATED_AT));
            projectOut.put("updatedAt", dbUserProject.get(PROJECT.UPDATED_AT));

            if (userId != null) {
                if (dbUserProject.get(PROJECT.USER_ID).equals(userId))
                    projectOut.put("permission", 0x4);
            }

        }
        return projectOut;
    }

    public static List<Map<String, Object>> getProjectsByUserId(long userId) {
        List<Long> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.USER_ID.eq(userId))
                .fetch(0, long.class);


        List<Map<String, Object>> projectListOut = new ArrayList<>();
        for (long projectId : dbUserProject) {
            projectListOut.add(ProjectUtilities.getProjectById(projectId, userId));
        }
        return projectListOut;
    }

    public static List<Map<String, Object>> getProjectMembersByProjectId(long projectId) {

        //TODO Catch TooManyRowsException
        Record5<Long, String, String, Timestamp, Long> dbUserProject = DiluvAPI.getDSLContext().select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT, PROJECT.ID)
                .from(PROJECT.join(USER).on(PROJECT.USER_ID.eq(USER.ID)))
                .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(projectId)))
                .fetchOne();

        List<Map<String, Object>> projectListOut = new ArrayList<>();

        if (dbUserProject != null) {
            Result<Record5<Long, String, String, Timestamp, String>> dbUser = DiluvAPI.getDSLContext().select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT, PROJECT_MEMBER.ROLE)
                    .from(USER.join(PROJECT_MEMBER).on(USER.ID.eq(PROJECT_MEMBER.USER_ID)))
                    .where(PROJECT_MEMBER.PROJECT_ID.eq(projectId))
                    .fetch();


            for (Record5<Long, String, String, Timestamp, String> user : dbUser) {
                Map<String, Object> owner = new HashMap<>();
                owner.put("username", user.get(USER.USERNAME));
                owner.put("avatar", user.get(USER.AVATAR));
                owner.put("createdAt", user.get(USER.CREATED_AT));
                owner.put("role", user.get(PROJECT_MEMBER.ROLE));
                projectListOut.add(owner);
            }

            Map<String, Object> owner = new HashMap<>();
            owner.put("username", dbUserProject.get(USER.USERNAME));
            owner.put("avatar", dbUserProject.get(USER.AVATAR));
            owner.put("createdAt", dbUserProject.get(USER.CREATED_AT));
            owner.put("role", "Owner");
            projectListOut.add(owner);
        }
        return projectListOut;
    }

    public static List<Map<String, Object>> getProjectFileGameVersionsById(long projectFileId) {
        Result<Record2<String, Timestamp>> dbProjectGameVersion = DiluvAPI.getDSLContext().select(GAME_VERSION.VERSION, GAME_VERSION.CREATED_AT)
                .from(PROJECT_FILE_GAME_VERSION.join(GAME_VERSION).on(PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID.eq(GAME_VERSION.ID)))
                .where(PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID.eq(projectFileId))
                .fetch();

        List<Map<String, Object>> dataOut = new ArrayList<>();
        for (Record2<String, Timestamp> user : dbProjectGameVersion) {
            Map<String, Object> version = new HashMap<>();
            version.put("version", user.get(GAME_VERSION.VERSION));
            version.put("createdAt", user.get(GAME_VERSION.CREATED_AT));
            dataOut.add(version);
        }

        return dataOut;
    }

    public static List<Map<String, Object>> getProjectCategoriesById(long projectId) {
        Result<Record2<String, String>> dbOut = DiluvAPI.getDSLContext().select(PROJECT_TYPE_CATEGORY.NAME, PROJECT_TYPE_CATEGORY.DESCRIPTION)
                .from(PROJECT_CATEGORY.join(PROJECT_TYPE_CATEGORY).on(PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID.eq(PROJECT_TYPE_CATEGORY.ID)))
                .where(PROJECT_CATEGORY.PROJECT_ID.eq(projectId))
                .fetch();


        List<Map<String, Object>> dataOut = new ArrayList<>();
        for (Record2<String, String> user : dbOut) {
            Map<String, Object> version = new HashMap<>();
            version.put("name", user.get(PROJECT_TYPE_CATEGORY.NAME));
            version.put("description", user.get(PROJECT_TYPE_CATEGORY.DESCRIPTION));
            dataOut.add(version);
        }

        return dataOut;
    }

    public static List<Map<String, Object>> getProjectFilesById(long projectId) {
        //TODO Remove status for unauth requests
        String fileURL = System.getenv("fileURL");
        Result<Record10<Long, String, String, Long, String, String, Long, Timestamp, Long, Boolean>> dbProjectFiles = DiluvAPI.getDSLContext().select(PROJECT_FILE.ID, PROJECT_FILE.SHA256, PROJECT_FILE.FILE_NAME, PROJECT_FILE.SIZE, PROJECT_FILE.RELEASE_TYPE, PROJECT_FILE.DISPLAY_NAME, PROJECT_FILE.DOWNLOADS, PROJECT_FILE.CREATED_AT, PROJECT_FILE.PARENT_ID, PROJECT_FILE.PUBLIC)
                .from(PROJECT_FILE)
                .where(PROJECT_FILE.ID.eq(projectId))
                .fetch();

        List<Map<String, Object>> dataOut = new ArrayList<>();
        for (Record10<Long, String, String, Long, String, String, Long, Timestamp, Long, Boolean> it : dbProjectFiles) {
            Map<String, Object> version = new HashMap<>();
            version.put("sha256", it.get(PROJECT_FILE.SHA256));
            version.put("fileName", it.get(PROJECT_FILE.FILE_NAME));
            version.put("size", it.get(PROJECT_FILE.SIZE));
            version.put("releaseType", it.get(PROJECT_FILE.RELEASE_TYPE));
            version.put("displayName", it.get(PROJECT_FILE.DISPLAY_NAME));
            version.put("downloads", it.get(PROJECT_FILE.DOWNLOADS));
            version.put("createdAt", it.get(PROJECT_FILE.CREATED_AT));
            version.put("parentId", it.get(PROJECT_FILE.PARENT_ID));
            version.put("public", it.get(PROJECT_FILE.PUBLIC));
            version.put("gameVersions", ProjectUtilities.getProjectFileGameVersionsById(it.get(PROJECT_FILE.ID)));
            version.put("downloadUrl", fileURL + "/" + it.get(PROJECT_FILE.ID) + "/" + it.get(PROJECT_FILE.SHA256) + "/" + it.get(PROJECT_FILE.DISPLAY_NAME));
            dataOut.add(version);
        }

        return dataOut;
    }

    public static Long getProjectIdBySlug(String projectSlug, Long projectTypeId) {
        Long projectId = DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.SLUG.eq(projectSlug).and(PROJECT.PROJECT_TYPE_ID.eq(projectTypeId)))
                .fetchOne(0, long.class);

        return projectId;
    }

    public static void insertProjectFiles(String fileName, String displayName, long size, String releaseType, Long parentId, String projectSlug, long userId) {
        Record2<Long, String> dbProject = DiluvAPI.getDSLContext().select(PROJECT.ID, PROJECT.SLUG)
                .from(PROJECT)
                .where(PROJECT.SLUG.eq(projectSlug))
                .fetchOne();
        if (dbProject != null) {
            DiluvAPI.getDSLContext().insertInto(PROJECT_FILE, PROJECT_FILE.FILE_NAME, PROJECT_FILE.DISPLAY_NAME, PROJECT_FILE.SIZE, PROJECT_FILE.RELEASE_TYPE, PROJECT_FILE.PARENT_ID, PROJECT_FILE.PROJECT_ID, PROJECT_FILE.USER_ID)
                    .values(fileName, displayName, size, releaseType, parentId, dbProject.get(PROJECT.ID), userId)
                    .execute();
        }
    }

    public static void insertProject(String name, String description, String shortDescription, String slug, String logo, long projectTypeId, long userId) {
        DiluvAPI.getDSLContext().insertInto(PROJECT, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORT_DESCRIPTION, PROJECT.SLUG, PROJECT.LOGO, PROJECT.PROJECT_TYPE_ID, PROJECT.USER_ID)
                .values(name, description, shortDescription, slug, logo, projectTypeId, userId)
                .execute();
    }
}