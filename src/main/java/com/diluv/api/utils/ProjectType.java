package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import com.diluv.api.models.tables.records.ProjectRecord;
import com.diluv.api.models.tables.records.ProjectTypeCategoryRecord;
import com.diluv.api.models.tables.records.ProjectTypeRecord;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class ProjectType {

    private final Game game;
    private final Long projectTypeId;

    public ProjectType(Game game, String projectTypeSlug) {
        this.game = game;
        this.projectTypeId = this.getProjectTypeIdBySlug(projectTypeSlug);
    }

    public ProjectType(Game game, long projectTypeId) {
        this.game = game;
        this.projectTypeId = projectTypeId;
    }

    public Map<String, Object> getData() {
        ProjectTypeRecord dbProjectType = DiluvAPI.getDSLContext()
                .selectFrom(PROJECT_TYPE)
                .where(PROJECT_TYPE.ID.eq(this.projectTypeId))
                .fetchOne();

        Map<String, Object> projectTypeOut = new HashMap<>();

        if (dbProjectType != null) {
            Integer dbPermission = DiluvAPI.getDSLContext().select(PROJECT_TYPE_PERMISSION.PERMISSION_CREATE)
                    .from(PROJECT_TYPE_PERMISSION)
                    .where(PROJECT_TYPE_PERMISSION.ID.eq(this.projectTypeId))
                    .fetchOne(0, int.class);

            projectTypeOut.put("name", dbProjectType.getName());
            projectTypeOut.put("description", dbProjectType.getDescription());
            projectTypeOut.put("slug", dbProjectType.getSlug());
            projectTypeOut.put("categories", this.getCategories());

            if (dbPermission != null)
                projectTypeOut.put("permission", dbPermission);

        }
        return projectTypeOut;
    }

    public boolean doesProjectExist(String slug) {
        return DiluvAPI.getDSLContext().selectFrom(PROJECT)
                .where(PROJECT.PROJECT_TYPE_ID.eq(this.projectTypeId)
                        .and(PROJECT.DELETED.eq(false))
                        .and(PROJECT.SLUG.eq(slug)))
                .fetch() != null;
    }

    public int getProjectCount() {
        return DiluvAPI.getDSLContext().select(DSL.count())
                .from(PROJECT)
                .where(PROJECT.PROJECT_TYPE_ID.eq(this.projectTypeId))
                .fetchOne(0, int.class);
    }

    public List<Map<String, Object>> getCategories() {
        Result<ProjectTypeCategoryRecord> dbUserProject = DiluvAPI.getDSLContext()
                .selectFrom(PROJECT_TYPE_CATEGORY)
                .where(PROJECT_TYPE_CATEGORY.PROJECT_TYPE_ID.eq(this.projectTypeId))
                .fetch();

        List<Map<String, Object>> projectTypeCategoryListOut = new ArrayList<>();

        for (ProjectTypeCategoryRecord it : dbUserProject) {
            Map<String, Object> projectTypeCategory = new HashMap<>();
            projectTypeCategory.put("name", it.getName());
            projectTypeCategory.put("description", it.getDescription());
            projectTypeCategory.put("projectTypeCategory", it.getSlug());
            projectTypeCategoryListOut.add(projectTypeCategory);
        }

        return projectTypeCategoryListOut;
    }

    //TODO return an id
    public void insertProject(String name, String description, String shortDescription, String slug, String logo, long userId) {
        DiluvAPI.getDSLContext().insertInto(PROJECT, PROJECT.NAME, PROJECT.DESCRIPTION, PROJECT.SHORT_DESCRIPTION, PROJECT.SLUG, PROJECT.LOGO, PROJECT.PROJECT_TYPE_ID, PROJECT.USER_ID)
                .values(name, description, shortDescription, slug, logo, this.projectTypeId, userId)
                .execute();
    }

    //TODO Return a id
    public void insertProjectFiles(String fileName, String displayName, long size, String releaseType, Long parentId, String projectSlug, long userId) {
        ProjectRecord dbProject = DiluvAPI.getDSLContext().selectFrom(PROJECT)
                .where(PROJECT_TYPE.ID.eq(this.projectTypeId).and(PROJECT.SLUG.eq(projectSlug)))
                .fetchOne();
        if (dbProject != null) {
            DiluvAPI.getDSLContext().insertInto(PROJECT_FILE, PROJECT_FILE.FILE_NAME, PROJECT_FILE.DISPLAY_NAME, PROJECT_FILE.SIZE, PROJECT_FILE.RELEASE_TYPE, PROJECT_FILE.PARENT_ID, PROJECT_FILE.PROJECT_ID, PROJECT_FILE.USER_ID)
                    .values(fileName, displayName, size, releaseType, parentId, dbProject.getId(), userId)
                    .execute();
        }
    }

//    public List<Map<String, Object>> getProjectsByUserId(long userId) {
//        List<Long> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT.ID)
//                .from(PROJECT)
//                .where(PROJECT.USER_ID.eq(userId))
//                .fetch(0, long.class);
//
//
//        List<Map<String, Object>> projectListOut = new ArrayList<>();
//        for (long projectId : dbUserProject) {
//            projectListOut.add(this.getProjectById(projectId, userId));
//        }
//        return projectListOut;
//    }

    public Long getProjectTypeIdBySlug(String projectTypeSlug) {
        return DiluvAPI.getDSLContext().select(PROJECT_TYPE.ID)
                .from(PROJECT_TYPE)
                .where(PROJECT_TYPE.GAME_ID.eq(this.game.getGameId()).and(PROJECT_TYPE.SLUG.eq(projectTypeSlug)))
                .fetchOne(0, long.class);
    }

    public Long getId() {
        return this.projectTypeId;
    }
}