package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import com.diluv.api.models.tables.records.ProjectFileRecord;
import com.diluv.api.models.tables.records.ProjectRecord;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class Project {
    private final ProjectType projectType;
    private final long projectId;

    public Project(ProjectType projectType, String projectSlug) {
        this.projectType = projectType;
        this.projectId = this.getProjectIdBySlug(projectSlug);
    }

    public Project(ProjectType projectType, long projectId) {
        this.projectType = projectType;
        this.projectId = projectId;
    }

    public Map<String, Object> getData(Long userId) {
        ProjectRecord dbUserProject = DiluvAPI.getDSLContext().selectFrom(PROJECT)
                .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(this.projectId)))
                .fetchOne();

        Map<String, Object> projectOut = new HashMap<>();
        if (dbUserProject != null) {
            //        val gameVersions = getProjectGameVersionsById(conn, projectId)

            projectOut.put("name", dbUserProject.getName());
            projectOut.put("description", dbUserProject.getDescription());
            projectOut.put("shortDescription", dbUserProject.getShortDescription());
            projectOut.put("slug", dbUserProject.getSlug());
            projectOut.put("logo", dbUserProject.getLogo());
            projectOut.put("totalDownloads", dbUserProject.getTotalDownloads());
            //                "projectTypeId", dbUserProject[ModelProject.projectTypeId],
            projectOut.put("authors", this.getProjectMembers());
            //                "gameVersions", gameVersions,
            projectOut.put("categories", this.getProjectCategories());
            projectOut.put("createdAt", dbUserProject.getCreatedAt());
            projectOut.put("updatedAt", dbUserProject.getUpdatedAt());

            if (userId != null) {
                if (dbUserProject.get(PROJECT.USER_ID).equals(userId))
                    projectOut.put("permission", 0x4);
            }

        }
        return projectOut;
    }

    public List<Map<String, Object>> getProjectMembers() {

        //TODO Catch TooManyRowsException
        Record5<Long, String, String, Timestamp, Long> dbUserProject = DiluvAPI.getDSLContext().select(USER.ID, USER.USERNAME, USER.AVATAR, USER.CREATED_AT, PROJECT.ID)
                .from(PROJECT.join(USER).on(PROJECT.USER_ID.eq(USER.ID)))
                .where(PROJECT.DELETED.eq(false).and(PROJECT.ID.eq(this.projectId)))
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

    public List<Map<String, Object>> getProjectFileGameVersions() {
        Result<Record2<String, Timestamp>> dbProjectGameVersion = DiluvAPI.getDSLContext().select(GAME_VERSION.VERSION, GAME_VERSION.CREATED_AT)
                .from(PROJECT_FILE_GAME_VERSION.join(GAME_VERSION).on(PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID.eq(GAME_VERSION.ID)))
                .where(PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID.eq(this.projectId))
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

    public List<Map<String, Object>> getProjectCategories() {
        Result<Record2<String, String>> dbOut = DiluvAPI.getDSLContext().select(PROJECT_TYPE_CATEGORY.NAME, PROJECT_TYPE_CATEGORY.DESCRIPTION)
                .from(PROJECT_CATEGORY.join(PROJECT_TYPE_CATEGORY).on(PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID.eq(PROJECT_TYPE_CATEGORY.ID)))
                .where(PROJECT_CATEGORY.PROJECT_ID.eq(this.projectId))
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

    public List<Map<String, Object>> getProjectFiles() {
        //TODO Remove status for unauth requests
        String fileURL = System.getenv("fileURL");
        Result<ProjectFileRecord> dbProjectFiles = DiluvAPI.getDSLContext().selectFrom(PROJECT_FILE)
                .where(PROJECT_FILE.ID.eq(this.projectId))
                .fetch();

        List<Map<String, Object>> dataOut = new ArrayList<>();
        for (ProjectFileRecord it : dbProjectFiles) {
            Map<String, Object> version = new HashMap<>();
            version.put("sha512", it.getSha512());
            version.put("fileName", it.getFileName());
            version.put("size", it.getSize());
            version.put("releaseType", it.getReleaseType());
            version.put("displayName", it.getDisplayName());
            version.put("downloads", it.getDownloads());
            version.put("createdAt", it.getCreatedAt());
            version.put("parentId", it.getParentId());
            version.put("public", it.getPublic());
            version.put("gameVersions", this.getProjectFileGameVersions());
            version.put("downloadUrl", fileURL + "/" + it.get(PROJECT_FILE.ID) + "/" + it.get(PROJECT_FILE.SHA512) + "/" + it.get(PROJECT_FILE.DISPLAY_NAME));
            dataOut.add(version);
        }

        return dataOut;
    }

    public Long getProjectIdBySlug(String projectSlug) {
        return DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.PROJECT_TYPE_ID.eq(this.projectType.getId()).and(PROJECT.SLUG.eq(projectSlug)))
                .fetchOne(0, long.class);
    }

    public long getId() {
        return projectId;
    }
}