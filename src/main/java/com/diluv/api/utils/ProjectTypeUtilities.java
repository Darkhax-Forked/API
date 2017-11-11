package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;


public class ProjectTypeUtilities {
    public static List<Map<String, Object>> getCategoriesByProjectTypeId(long projectTypeId) {
        Result<Record3<String, String, String>> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT_TYPE_CATEGORY.NAME, PROJECT_TYPE_CATEGORY.DESCRIPTION, PROJECT_TYPE_CATEGORY.SLUG)
                .from(PROJECT_TYPE_CATEGORY)
                .where(PROJECT_TYPE_CATEGORY.PROJECT_TYPE_ID.eq(projectTypeId))
                .fetch();

        List<Map<String, Object>> projectTypeCategoryListOut = new ArrayList<>();

        for (Record3<String, String, String> it : dbUserProject) {
            Map<String, Object> projectTypeCategory = new HashMap<>();
            projectTypeCategory.put("name", it.get(PROJECT_TYPE_CATEGORY.NAME));
            projectTypeCategory.put("description", it.get(PROJECT_TYPE_CATEGORY.DESCRIPTION));
            projectTypeCategory.put("projectTypeCategory", it.get(PROJECT_TYPE_CATEGORY.SLUG));
            projectTypeCategoryListOut.add(projectTypeCategory);
        }

        return projectTypeCategoryListOut;
    }

    public static List<Map<String, Object>> getProjectsByProjectTypeId(long projectTypeId) {
        Result<Record1<Long>> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.PROJECT_TYPE_ID.eq(projectTypeId))
                .fetch();


        List<Map<String, Object>> projectListOut = new ArrayList<>();
        for (Record1<Long> it : dbUserProject) {
            projectListOut.add(ProjectUtilities.getProjectById(it.get(PROJECT.ID), null));
        }

        return projectListOut;
    }

    public static Map<String, Object> getProjectTypeById(long projectTypeId) {
        Record3<String, String, String> dbUserProject = DiluvAPI.getDSLContext().select(PROJECT_TYPE.NAME, PROJECT_TYPE.DESCRIPTION, PROJECT_TYPE.SLUG)
                .from(PROJECT_TYPE)
                .where(PROJECT_TYPE.ID.eq(projectTypeId))
                .fetchOne();

        Map<String, Object> projectTypeOut = new HashMap<>();

        if (dbUserProject != null) {
            Integer dbPermission = DiluvAPI.getDSLContext().select(PROJECT_TYPE_PERMISSION.PERMISSION_CREATE)
                    .from(PROJECT_TYPE_PERMISSION)
                    .where(PROJECT_TYPE_PERMISSION.ID.eq(projectTypeId))
                    .fetchOne(0, int.class);

            projectTypeOut.put("name", dbUserProject.get(PROJECT_TYPE.NAME));
            projectTypeOut.put("description", dbUserProject.get(PROJECT_TYPE.DESCRIPTION));
            projectTypeOut.put("slug", dbUserProject.get(PROJECT_TYPE.SLUG));
            projectTypeOut.put("categories", ProjectTypeUtilities.getCategoriesByProjectTypeId(projectTypeId));

            if (dbPermission != null)
                projectTypeOut.put("permission", dbPermission);

        }
        return projectTypeOut;
    }

    public static Long getProjectTypeIdBySlug(String projectTypeSlug) {
        return DiluvAPI.getDSLContext().select(PROJECT_TYPE.ID)
                .from(PROJECT_TYPE)
                .where(PROJECT_TYPE.SLUG.eq(projectTypeSlug))
                .fetchOne(0, long.class);
    }

    public static Long getProjectIdBySlugAndProjectTypeId(String projectSlug) {
        return DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.SLUG.eq(projectSlug))
                .fetchOne(0, long.class);
    }
}