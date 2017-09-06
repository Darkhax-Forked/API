/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models;


import com.diluv.api.models.tables.*;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Diluv extends SchemaImpl {

    private static final long serialVersionUID = 2073260419;

    /**
     * The reference instance of <code>diluv</code>
     */
    public static final Diluv DILUV = new Diluv();

    /**
     * The table <code>diluv.AUTH_ACCESS_TOKEN</code>.
     */
    public final AuthAccessToken AUTH_ACCESS_TOKEN = com.diluv.api.models.tables.AuthAccessToken.AUTH_ACCESS_TOKEN;

    /**
     * The table <code>diluv.AUTH_MFA_TOKEN</code>.
     */
    public final AuthMfaToken AUTH_MFA_TOKEN = com.diluv.api.models.tables.AuthMfaToken.AUTH_MFA_TOKEN;

    /**
     * The table <code>diluv.GAME</code>.
     */
    public final Game GAME = com.diluv.api.models.tables.Game.GAME;

    /**
     * The table <code>diluv.GAME_VERSION</code>.
     */
    public final GameVersion GAME_VERSION = com.diluv.api.models.tables.GameVersion.GAME_VERSION;

    /**
     * The table <code>diluv.PROJECT</code>.
     */
    public final Project PROJECT = com.diluv.api.models.tables.Project.PROJECT;

    /**
     * The table <code>diluv.PROJECT_CATEGORY</code>.
     */
    public final ProjectCategory PROJECT_CATEGORY = com.diluv.api.models.tables.ProjectCategory.PROJECT_CATEGORY;

    /**
     * The table <code>diluv.PROJECT_COMMENT</code>.
     */
    public final ProjectComment PROJECT_COMMENT = com.diluv.api.models.tables.ProjectComment.PROJECT_COMMENT;

    /**
     * The table <code>diluv.PROJECT_FILE</code>.
     */
    public final ProjectFile PROJECT_FILE = com.diluv.api.models.tables.ProjectFile.PROJECT_FILE;

    /**
     * The table <code>diluv.PROJECT_FILE_DOWNLOAD</code>.
     */
    public final ProjectFileDownload PROJECT_FILE_DOWNLOAD = com.diluv.api.models.tables.ProjectFileDownload.PROJECT_FILE_DOWNLOAD;

    /**
     * The table <code>diluv.PROJECT_FILE_GAME_VERSION</code>.
     */
    public final ProjectFileGameVersion PROJECT_FILE_GAME_VERSION = com.diluv.api.models.tables.ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION;

    /**
     * The table <code>diluv.PROJECT_FILE_PROCESSING</code>.
     */
    public final ProjectFileProcessing PROJECT_FILE_PROCESSING = com.diluv.api.models.tables.ProjectFileProcessing.PROJECT_FILE_PROCESSING;

    /**
     * The table <code>diluv.PROJECT_FILE_PROCESSING_LOG</code>.
     */
    public final ProjectFileProcessingLog PROJECT_FILE_PROCESSING_LOG = com.diluv.api.models.tables.ProjectFileProcessingLog.PROJECT_FILE_PROCESSING_LOG;

    /**
     * The table <code>diluv.PROJECT_MEMBER</code>.
     */
    public final ProjectMember PROJECT_MEMBER = com.diluv.api.models.tables.ProjectMember.PROJECT_MEMBER;

    /**
     * The table <code>diluv.PROJECT_TYPE</code>.
     */
    public final ProjectType PROJECT_TYPE = com.diluv.api.models.tables.ProjectType.PROJECT_TYPE;

    /**
     * The table <code>diluv.PROJECT_TYPE_CATEGORY</code>.
     */
    public final ProjectTypeCategory PROJECT_TYPE_CATEGORY = com.diluv.api.models.tables.ProjectTypeCategory.PROJECT_TYPE_CATEGORY;

    /**
     * The table <code>diluv.PROJECT_TYPE_PERMISSION</code>.
     */
    public final ProjectTypePermission PROJECT_TYPE_PERMISSION = com.diluv.api.models.tables.ProjectTypePermission.PROJECT_TYPE_PERMISSION;

    /**
     * The table <code>diluv.USER</code>.
     */
    public final User USER = com.diluv.api.models.tables.User.USER;

    /**
     * The table <code>diluv.USER_BETA_KEY</code>.
     */
    public final UserBetaKey USER_BETA_KEY = com.diluv.api.models.tables.UserBetaKey.USER_BETA_KEY;

    /**
     * No further instances allowed
     */
    private Diluv() {
        super("diluv", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
                AuthAccessToken.AUTH_ACCESS_TOKEN,
                AuthMfaToken.AUTH_MFA_TOKEN,
                Game.GAME,
                GameVersion.GAME_VERSION,
                Project.PROJECT,
                ProjectCategory.PROJECT_CATEGORY,
                ProjectComment.PROJECT_COMMENT,
                ProjectFile.PROJECT_FILE,
                ProjectFileDownload.PROJECT_FILE_DOWNLOAD,
                ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION,
                ProjectFileProcessing.PROJECT_FILE_PROCESSING,
                ProjectFileProcessingLog.PROJECT_FILE_PROCESSING_LOG,
                ProjectMember.PROJECT_MEMBER,
                ProjectType.PROJECT_TYPE,
                ProjectTypeCategory.PROJECT_TYPE_CATEGORY,
                ProjectTypePermission.PROJECT_TYPE_PERMISSION,
                User.USER,
                UserBetaKey.USER_BETA_KEY);
    }
}
