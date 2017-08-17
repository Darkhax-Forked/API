/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models;


import com.diluv.api.models.tables.*;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in diluv
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Tables {

    /**
     * The table <code>diluv.AUTH_ACCESS_TOKEN</code>.
     */
    public static final AuthAccessToken AUTH_ACCESS_TOKEN = com.diluv.api.models.tables.AuthAccessToken.AUTH_ACCESS_TOKEN;

    /**
     * The table <code>diluv.AUTH_MFA_TOKEN</code>.
     */
    public static final AuthMfaToken AUTH_MFA_TOKEN = com.diluv.api.models.tables.AuthMfaToken.AUTH_MFA_TOKEN;

    /**
     * The table <code>diluv.GAME</code>.
     */
    public static final Game GAME = com.diluv.api.models.tables.Game.GAME;

    /**
     * The table <code>diluv.GAME_VERSION</code>.
     */
    public static final GameVersion GAME_VERSION = com.diluv.api.models.tables.GameVersion.GAME_VERSION;

    /**
     * The table <code>diluv.PROJECT</code>.
     */
    public static final Project PROJECT = com.diluv.api.models.tables.Project.PROJECT;

    /**
     * The table <code>diluv.PROJECT_CATEGORY</code>.
     */
    public static final ProjectCategory PROJECT_CATEGORY = com.diluv.api.models.tables.ProjectCategory.PROJECT_CATEGORY;

    /**
     * The table <code>diluv.PROJECT_COMMENT</code>.
     */
    public static final ProjectComment PROJECT_COMMENT = com.diluv.api.models.tables.ProjectComment.PROJECT_COMMENT;

    /**
     * The table <code>diluv.PROJECT_FILE</code>.
     */
    public static final ProjectFile PROJECT_FILE = com.diluv.api.models.tables.ProjectFile.PROJECT_FILE;

    /**
     * The table <code>diluv.PROJECT_FILE_GAME_VERSION</code>.
     */
    public static final ProjectFileGameVersion PROJECT_FILE_GAME_VERSION = com.diluv.api.models.tables.ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION;

    /**
     * The table <code>diluv.PROJECT_FILE_PROCESSING</code>.
     */
    public static final ProjectFileProcessing PROJECT_FILE_PROCESSING = com.diluv.api.models.tables.ProjectFileProcessing.PROJECT_FILE_PROCESSING;

    /**
     * The table <code>diluv.PROJECT_MEMBER</code>.
     */
    public static final ProjectMember PROJECT_MEMBER = com.diluv.api.models.tables.ProjectMember.PROJECT_MEMBER;

    /**
     * The table <code>diluv.PROJECT_TYPE</code>.
     */
    public static final ProjectType PROJECT_TYPE = com.diluv.api.models.tables.ProjectType.PROJECT_TYPE;

    /**
     * The table <code>diluv.PROJECT_TYPE_CATEGORY</code>.
     */
    public static final ProjectTypeCategory PROJECT_TYPE_CATEGORY = com.diluv.api.models.tables.ProjectTypeCategory.PROJECT_TYPE_CATEGORY;

    /**
     * The table <code>diluv.PROJECT_TYPE_PERMISSION</code>.
     */
    public static final ProjectTypePermission PROJECT_TYPE_PERMISSION = com.diluv.api.models.tables.ProjectTypePermission.PROJECT_TYPE_PERMISSION;

    /**
     * The table <code>diluv.USER</code>.
     */
    public static final User USER = com.diluv.api.models.tables.User.USER;

    /**
     * The table <code>diluv.USER_BETA_KEY</code>.
     */
    public static final UserBetaKey USER_BETA_KEY = com.diluv.api.models.tables.UserBetaKey.USER_BETA_KEY;
}
