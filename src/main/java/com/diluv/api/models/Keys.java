/*
 * This file is generated by jOOQ.
*/
package com.diluv.api.models;


import com.diluv.api.models.tables.*;
import com.diluv.api.models.tables.records.*;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;

import javax.annotation.Generated;


/**
 * A class modelling foreign key relationships between tables of the <code>diluv</code>
 * schema
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.9.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<GameRecord, Long> IDENTITY_GAME = Identities0.IDENTITY_GAME;
    public static final Identity<GameVersionRecord, Long> IDENTITY_GAME_VERSION = Identities0.IDENTITY_GAME_VERSION;
    public static final Identity<ProjectRecord, Long> IDENTITY_PROJECT = Identities0.IDENTITY_PROJECT;
    public static final Identity<ProjectCommentRecord, Long> IDENTITY_PROJECT_COMMENT = Identities0.IDENTITY_PROJECT_COMMENT;
    public static final Identity<ProjectFileRecord, Long> IDENTITY_PROJECT_FILE = Identities0.IDENTITY_PROJECT_FILE;
    public static final Identity<ProjectMemberRecord, Long> IDENTITY_PROJECT_MEMBER = Identities0.IDENTITY_PROJECT_MEMBER;
    public static final Identity<ProjectTypeRecord, Long> IDENTITY_PROJECT_TYPE = Identities0.IDENTITY_PROJECT_TYPE;
    public static final Identity<ProjectTypeCategoryRecord, Long> IDENTITY_PROJECT_TYPE_CATEGORY = Identities0.IDENTITY_PROJECT_TYPE_CATEGORY;
    public static final Identity<ProjectTypePermissionRecord, Long> IDENTITY_PROJECT_TYPE_PERMISSION = Identities0.IDENTITY_PROJECT_TYPE_PERMISSION;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AuthAccessTokenRecord> KEY_AUTH_ACCESS_TOKEN_PRIMARY = UniqueKeys0.KEY_AUTH_ACCESS_TOKEN_PRIMARY;
    public static final UniqueKey<AuthAccessTokenRecord> KEY_AUTH_ACCESS_TOKEN_REFRESH_TOKEN = UniqueKeys0.KEY_AUTH_ACCESS_TOKEN_REFRESH_TOKEN;
    public static final UniqueKey<AuthMfaTokenRecord> KEY_AUTH_MFA_TOKEN_PRIMARY = UniqueKeys0.KEY_AUTH_MFA_TOKEN_PRIMARY;
    public static final UniqueKey<AuthVerifyTokenRecord> KEY_AUTH_VERIFY_TOKEN_PRIMARY = UniqueKeys0.KEY_AUTH_VERIFY_TOKEN_PRIMARY;
    public static final UniqueKey<GameRecord> KEY_GAME_PRIMARY = UniqueKeys0.KEY_GAME_PRIMARY;
    public static final UniqueKey<GameRecord> KEY_GAME_NAME = UniqueKeys0.KEY_GAME_NAME;
    public static final UniqueKey<GameRecord> KEY_GAME_SLUG = UniqueKeys0.KEY_GAME_SLUG;
    public static final UniqueKey<GameVersionRecord> KEY_GAME_VERSION_PRIMARY = UniqueKeys0.KEY_GAME_VERSION_PRIMARY;
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = UniqueKeys0.KEY_PROJECT_PRIMARY;
    public static final UniqueKey<ProjectCategoryRecord> KEY_PROJECT_CATEGORY_PRIMARY = UniqueKeys0.KEY_PROJECT_CATEGORY_PRIMARY;
    public static final UniqueKey<ProjectCommentRecord> KEY_PROJECT_COMMENT_PRIMARY = UniqueKeys0.KEY_PROJECT_COMMENT_PRIMARY;
    public static final UniqueKey<ProjectFileRecord> KEY_PROJECT_FILE_PRIMARY = UniqueKeys0.KEY_PROJECT_FILE_PRIMARY;
    public static final UniqueKey<ProjectFileDownloadRecord> KEY_PROJECT_FILE_DOWNLOAD_PRIMARY = UniqueKeys0.KEY_PROJECT_FILE_DOWNLOAD_PRIMARY;
    public static final UniqueKey<ProjectFileGameVersionRecord> KEY_PROJECT_FILE_GAME_VERSION_PRIMARY = UniqueKeys0.KEY_PROJECT_FILE_GAME_VERSION_PRIMARY;
    public static final UniqueKey<ProjectFileProcessingRecord> KEY_PROJECT_FILE_PROCESSING_PRIMARY = UniqueKeys0.KEY_PROJECT_FILE_PROCESSING_PRIMARY;
    public static final UniqueKey<ProjectMemberRecord> KEY_PROJECT_MEMBER_PRIMARY = UniqueKeys0.KEY_PROJECT_MEMBER_PRIMARY;
    public static final UniqueKey<ProjectTypeRecord> KEY_PROJECT_TYPE_PRIMARY = UniqueKeys0.KEY_PROJECT_TYPE_PRIMARY;
    public static final UniqueKey<ProjectTypeCategoryRecord> KEY_PROJECT_TYPE_CATEGORY_PRIMARY = UniqueKeys0.KEY_PROJECT_TYPE_CATEGORY_PRIMARY;
    public static final UniqueKey<ProjectTypePermissionRecord> KEY_PROJECT_TYPE_PERMISSION_PRIMARY = UniqueKeys0.KEY_PROJECT_TYPE_PERMISSION_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL = UniqueKeys0.KEY_USER_EMAIL;
    public static final UniqueKey<UserRecord> KEY_USER_USERNAME = UniqueKeys0.KEY_USER_USERNAME;
    public static final UniqueKey<UserBetaKeyRecord> KEY_USER_BETA_KEY_PRIMARY = UniqueKeys0.KEY_USER_BETA_KEY_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AuthAccessTokenRecord, UserRecord> AUTH_ACCESS_TOKEN_IBFK_1 = ForeignKeys0.AUTH_ACCESS_TOKEN_IBFK_1;
    public static final ForeignKey<AuthMfaTokenRecord, UserRecord> AUTH_MFA_TOKEN_IBFK_1 = ForeignKeys0.AUTH_MFA_TOKEN_IBFK_1;
    public static final ForeignKey<AuthVerifyTokenRecord, UserRecord> AUTH_VERIFY_TOKEN_IBFK_1 = ForeignKeys0.AUTH_VERIFY_TOKEN_IBFK_1;
    public static final ForeignKey<GameVersionRecord, GameRecord> GAME_VERSION_IBFK_1 = ForeignKeys0.GAME_VERSION_IBFK_1;
    public static final ForeignKey<ProjectRecord, ProjectTypeRecord> PROJECT_IBFK_1 = ForeignKeys0.PROJECT_IBFK_1;
    public static final ForeignKey<ProjectRecord, UserRecord> PROJECT_IBFK_2 = ForeignKeys0.PROJECT_IBFK_2;
    public static final ForeignKey<ProjectCategoryRecord, ProjectRecord> PROJECT_CATEGORY_IBFK_1 = ForeignKeys0.PROJECT_CATEGORY_IBFK_1;
    public static final ForeignKey<ProjectCategoryRecord, ProjectTypeCategoryRecord> PROJECT_CATEGORY_IBFK_2 = ForeignKeys0.PROJECT_CATEGORY_IBFK_2;
    public static final ForeignKey<ProjectCommentRecord, ProjectCommentRecord> PROJECT_COMMENT_IBFK_1 = ForeignKeys0.PROJECT_COMMENT_IBFK_1;
    public static final ForeignKey<ProjectCommentRecord, ProjectRecord> PROJECT_COMMENT_IBFK_2 = ForeignKeys0.PROJECT_COMMENT_IBFK_2;
    public static final ForeignKey<ProjectCommentRecord, UserRecord> PROJECT_COMMENT_IBFK_3 = ForeignKeys0.PROJECT_COMMENT_IBFK_3;
    public static final ForeignKey<ProjectFileRecord, ProjectFileRecord> PROJECT_FILE_IBFK_1 = ForeignKeys0.PROJECT_FILE_IBFK_1;
    public static final ForeignKey<ProjectFileRecord, ProjectRecord> PROJECT_FILE_IBFK_2 = ForeignKeys0.PROJECT_FILE_IBFK_2;
    public static final ForeignKey<ProjectFileRecord, UserRecord> PROJECT_FILE_IBFK_3 = ForeignKeys0.PROJECT_FILE_IBFK_3;
    public static final ForeignKey<ProjectFileDownloadRecord, ProjectFileRecord> PROJECT_FILE_DOWNLOAD_IBFK_1 = ForeignKeys0.PROJECT_FILE_DOWNLOAD_IBFK_1;
    public static final ForeignKey<ProjectFileGameVersionRecord, ProjectFileRecord> PROJECT_FILE_GAME_VERSION_IBFK_1 = ForeignKeys0.PROJECT_FILE_GAME_VERSION_IBFK_1;
    public static final ForeignKey<ProjectFileGameVersionRecord, GameVersionRecord> PROJECT_FILE_GAME_VERSION_IBFK_2 = ForeignKeys0.PROJECT_FILE_GAME_VERSION_IBFK_2;
    public static final ForeignKey<ProjectFileProcessingRecord, ProjectFileRecord> PROJECT_FILE_PROCESSING_IBFK_1 = ForeignKeys0.PROJECT_FILE_PROCESSING_IBFK_1;
    public static final ForeignKey<ProjectMemberRecord, ProjectRecord> PROJECT_MEMBER_IBFK_2 = ForeignKeys0.PROJECT_MEMBER_IBFK_2;
    public static final ForeignKey<ProjectMemberRecord, UserRecord> PROJECT_MEMBER_IBFK_1 = ForeignKeys0.PROJECT_MEMBER_IBFK_1;
    public static final ForeignKey<ProjectTypeRecord, GameRecord> PROJECT_TYPE_IBFK_1 = ForeignKeys0.PROJECT_TYPE_IBFK_1;
    public static final ForeignKey<ProjectTypeCategoryRecord, ProjectTypeRecord> PROJECT_TYPE_CATEGORY_IBFK_1 = ForeignKeys0.PROJECT_TYPE_CATEGORY_IBFK_1;
    public static final ForeignKey<ProjectTypePermissionRecord, ProjectTypeRecord> PROJECT_TYPE_PERMISSION_IBFK_1 = ForeignKeys0.PROJECT_TYPE_PERMISSION_IBFK_1;
    public static final ForeignKey<UserBetaKeyRecord, UserRecord> USER_BETA_KEY_IBFK_1 = ForeignKeys0.USER_BETA_KEY_IBFK_1;
    public static final ForeignKey<UserBetaKeyRecord, UserRecord> USER_BETA_KEY_IBFK_2 = ForeignKeys0.USER_BETA_KEY_IBFK_2;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<GameRecord, Long> IDENTITY_GAME = createIdentity(Game.GAME, Game.GAME.ID);
        public static Identity<GameVersionRecord, Long> IDENTITY_GAME_VERSION = createIdentity(GameVersion.GAME_VERSION, GameVersion.GAME_VERSION.ID);
        public static Identity<ProjectRecord, Long> IDENTITY_PROJECT = createIdentity(Project.PROJECT, Project.PROJECT.ID);
        public static Identity<ProjectCommentRecord, Long> IDENTITY_PROJECT_COMMENT = createIdentity(ProjectComment.PROJECT_COMMENT, ProjectComment.PROJECT_COMMENT.ID);
        public static Identity<ProjectFileRecord, Long> IDENTITY_PROJECT_FILE = createIdentity(ProjectFile.PROJECT_FILE, ProjectFile.PROJECT_FILE.ID);
        public static Identity<ProjectMemberRecord, Long> IDENTITY_PROJECT_MEMBER = createIdentity(ProjectMember.PROJECT_MEMBER, ProjectMember.PROJECT_MEMBER.ID);
        public static Identity<ProjectTypeRecord, Long> IDENTITY_PROJECT_TYPE = createIdentity(ProjectType.PROJECT_TYPE, ProjectType.PROJECT_TYPE.ID);
        public static Identity<ProjectTypeCategoryRecord, Long> IDENTITY_PROJECT_TYPE_CATEGORY = createIdentity(ProjectTypeCategory.PROJECT_TYPE_CATEGORY, ProjectTypeCategory.PROJECT_TYPE_CATEGORY.ID);
        public static Identity<ProjectTypePermissionRecord, Long> IDENTITY_PROJECT_TYPE_PERMISSION = createIdentity(ProjectTypePermission.PROJECT_TYPE_PERMISSION, ProjectTypePermission.PROJECT_TYPE_PERMISSION.ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<AuthAccessTokenRecord> KEY_AUTH_ACCESS_TOKEN_PRIMARY = createUniqueKey(AuthAccessToken.AUTH_ACCESS_TOKEN, "KEY_AUTH_ACCESS_TOKEN_PRIMARY", AuthAccessToken.AUTH_ACCESS_TOKEN.TOKEN);
        public static final UniqueKey<AuthAccessTokenRecord> KEY_AUTH_ACCESS_TOKEN_REFRESH_TOKEN = createUniqueKey(AuthAccessToken.AUTH_ACCESS_TOKEN, "KEY_AUTH_ACCESS_TOKEN_REFRESH_TOKEN", AuthAccessToken.AUTH_ACCESS_TOKEN.REFRESH_TOKEN);
        public static final UniqueKey<AuthMfaTokenRecord> KEY_AUTH_MFA_TOKEN_PRIMARY = createUniqueKey(AuthMfaToken.AUTH_MFA_TOKEN, "KEY_AUTH_MFA_TOKEN_PRIMARY", AuthMfaToken.AUTH_MFA_TOKEN.TOKEN);
        public static final UniqueKey<AuthVerifyTokenRecord> KEY_AUTH_VERIFY_TOKEN_PRIMARY = createUniqueKey(AuthVerifyToken.AUTH_VERIFY_TOKEN, "KEY_AUTH_VERIFY_TOKEN_PRIMARY", AuthVerifyToken.AUTH_VERIFY_TOKEN.USER_ID);
        public static final UniqueKey<GameRecord> KEY_GAME_PRIMARY = createUniqueKey(Game.GAME, "KEY_GAME_PRIMARY", Game.GAME.ID);
        public static final UniqueKey<GameRecord> KEY_GAME_NAME = createUniqueKey(Game.GAME, "KEY_GAME_NAME", Game.GAME.NAME);
        public static final UniqueKey<GameRecord> KEY_GAME_SLUG = createUniqueKey(Game.GAME, "KEY_GAME_SLUG", Game.GAME.SLUG);
        public static final UniqueKey<GameVersionRecord> KEY_GAME_VERSION_PRIMARY = createUniqueKey(GameVersion.GAME_VERSION, "KEY_GAME_VERSION_PRIMARY", GameVersion.GAME_VERSION.ID);
        public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = createUniqueKey(Project.PROJECT, "KEY_PROJECT_PRIMARY", Project.PROJECT.ID);
        public static final UniqueKey<ProjectCategoryRecord> KEY_PROJECT_CATEGORY_PRIMARY = createUniqueKey(ProjectCategory.PROJECT_CATEGORY, "KEY_PROJECT_CATEGORY_PRIMARY", ProjectCategory.PROJECT_CATEGORY.PROJECT_ID, ProjectCategory.PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID);
        public static final UniqueKey<ProjectCommentRecord> KEY_PROJECT_COMMENT_PRIMARY = createUniqueKey(ProjectComment.PROJECT_COMMENT, "KEY_PROJECT_COMMENT_PRIMARY", ProjectComment.PROJECT_COMMENT.ID);
        public static final UniqueKey<ProjectFileRecord> KEY_PROJECT_FILE_PRIMARY = createUniqueKey(ProjectFile.PROJECT_FILE, "KEY_PROJECT_FILE_PRIMARY", ProjectFile.PROJECT_FILE.ID);
        public static final UniqueKey<ProjectFileDownloadRecord> KEY_PROJECT_FILE_DOWNLOAD_PRIMARY = createUniqueKey(ProjectFileDownload.PROJECT_FILE_DOWNLOAD, "KEY_PROJECT_FILE_DOWNLOAD_PRIMARY", ProjectFileDownload.PROJECT_FILE_DOWNLOAD.PROJECT_FILE_ID);
        public static final UniqueKey<ProjectFileGameVersionRecord> KEY_PROJECT_FILE_GAME_VERSION_PRIMARY = createUniqueKey(ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION, "KEY_PROJECT_FILE_GAME_VERSION_PRIMARY", ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID, ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID);
        public static final UniqueKey<ProjectFileProcessingRecord> KEY_PROJECT_FILE_PROCESSING_PRIMARY = createUniqueKey(ProjectFileProcessing.PROJECT_FILE_PROCESSING, "KEY_PROJECT_FILE_PROCESSING_PRIMARY", ProjectFileProcessing.PROJECT_FILE_PROCESSING.PROJECT_FILE_ID, ProjectFileProcessing.PROJECT_FILE_PROCESSING.STATUS);
        public static final UniqueKey<ProjectMemberRecord> KEY_PROJECT_MEMBER_PRIMARY = createUniqueKey(ProjectMember.PROJECT_MEMBER, "KEY_PROJECT_MEMBER_PRIMARY", ProjectMember.PROJECT_MEMBER.ID);
        public static final UniqueKey<ProjectTypeRecord> KEY_PROJECT_TYPE_PRIMARY = createUniqueKey(ProjectType.PROJECT_TYPE, "KEY_PROJECT_TYPE_PRIMARY", ProjectType.PROJECT_TYPE.ID);
        public static final UniqueKey<ProjectTypeCategoryRecord> KEY_PROJECT_TYPE_CATEGORY_PRIMARY = createUniqueKey(ProjectTypeCategory.PROJECT_TYPE_CATEGORY, "KEY_PROJECT_TYPE_CATEGORY_PRIMARY", ProjectTypeCategory.PROJECT_TYPE_CATEGORY.ID);
        public static final UniqueKey<ProjectTypePermissionRecord> KEY_PROJECT_TYPE_PERMISSION_PRIMARY = createUniqueKey(ProjectTypePermission.PROJECT_TYPE_PERMISSION, "KEY_PROJECT_TYPE_PERMISSION_PRIMARY", ProjectTypePermission.PROJECT_TYPE_PERMISSION.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_USER_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserRecord> KEY_USER_EMAIL = createUniqueKey(User.USER, "KEY_USER_EMAIL", User.USER.EMAIL);
        public static final UniqueKey<UserRecord> KEY_USER_USERNAME = createUniqueKey(User.USER, "KEY_USER_USERNAME", User.USER.USERNAME);
        public static final UniqueKey<UserBetaKeyRecord> KEY_USER_BETA_KEY_PRIMARY = createUniqueKey(UserBetaKey.USER_BETA_KEY, "KEY_USER_BETA_KEY_PRIMARY", UserBetaKey.USER_BETA_KEY.BETA_KEY);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<AuthAccessTokenRecord, UserRecord> AUTH_ACCESS_TOKEN_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, AuthAccessToken.AUTH_ACCESS_TOKEN, "AUTH_ACCESS_TOKEN_ibfk_1", AuthAccessToken.AUTH_ACCESS_TOKEN.USER_ID);
        public static final ForeignKey<AuthMfaTokenRecord, UserRecord> AUTH_MFA_TOKEN_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, AuthMfaToken.AUTH_MFA_TOKEN, "AUTH_MFA_TOKEN_ibfk_1", AuthMfaToken.AUTH_MFA_TOKEN.USER_ID);
        public static final ForeignKey<AuthVerifyTokenRecord, UserRecord> AUTH_VERIFY_TOKEN_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, AuthVerifyToken.AUTH_VERIFY_TOKEN, "AUTH_VERIFY_TOKEN_ibfk_1", AuthVerifyToken.AUTH_VERIFY_TOKEN.USER_ID);
        public static final ForeignKey<GameVersionRecord, GameRecord> GAME_VERSION_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_GAME_PRIMARY, GameVersion.GAME_VERSION, "GAME_VERSION_ibfk_1", GameVersion.GAME_VERSION.GAME_ID);
        public static final ForeignKey<ProjectRecord, ProjectTypeRecord> PROJECT_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_TYPE_PRIMARY, Project.PROJECT, "PROJECT_ibfk_1", Project.PROJECT.PROJECT_TYPE_ID);
        public static final ForeignKey<ProjectRecord, UserRecord> PROJECT_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, Project.PROJECT, "PROJECT_ibfk_2", Project.PROJECT.USER_ID);
        public static final ForeignKey<ProjectCategoryRecord, ProjectRecord> PROJECT_CATEGORY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, ProjectCategory.PROJECT_CATEGORY, "PROJECT_CATEGORY_ibfk_1", ProjectCategory.PROJECT_CATEGORY.PROJECT_ID);
        public static final ForeignKey<ProjectCategoryRecord, ProjectTypeCategoryRecord> PROJECT_CATEGORY_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_TYPE_CATEGORY_PRIMARY, ProjectCategory.PROJECT_CATEGORY, "PROJECT_CATEGORY_ibfk_2", ProjectCategory.PROJECT_CATEGORY.PROJECT_TYPE_CATEGORY_ID);
        public static final ForeignKey<ProjectCommentRecord, ProjectCommentRecord> PROJECT_COMMENT_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_COMMENT_PRIMARY, ProjectComment.PROJECT_COMMENT, "PROJECT_COMMENT_ibfk_1", ProjectComment.PROJECT_COMMENT.PARENT_ID);
        public static final ForeignKey<ProjectCommentRecord, ProjectRecord> PROJECT_COMMENT_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, ProjectComment.PROJECT_COMMENT, "PROJECT_COMMENT_ibfk_2", ProjectComment.PROJECT_COMMENT.PROJECT_ID);
        public static final ForeignKey<ProjectCommentRecord, UserRecord> PROJECT_COMMENT_IBFK_3 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, ProjectComment.PROJECT_COMMENT, "PROJECT_COMMENT_ibfk_3", ProjectComment.PROJECT_COMMENT.USER_ID);
        public static final ForeignKey<ProjectFileRecord, ProjectFileRecord> PROJECT_FILE_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_FILE_PRIMARY, ProjectFile.PROJECT_FILE, "PROJECT_FILE_ibfk_1", ProjectFile.PROJECT_FILE.PARENT_ID);
        public static final ForeignKey<ProjectFileRecord, ProjectRecord> PROJECT_FILE_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, ProjectFile.PROJECT_FILE, "PROJECT_FILE_ibfk_2", ProjectFile.PROJECT_FILE.PROJECT_ID);
        public static final ForeignKey<ProjectFileRecord, UserRecord> PROJECT_FILE_IBFK_3 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, ProjectFile.PROJECT_FILE, "PROJECT_FILE_ibfk_3", ProjectFile.PROJECT_FILE.USER_ID);
        public static final ForeignKey<ProjectFileDownloadRecord, ProjectFileRecord> PROJECT_FILE_DOWNLOAD_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_FILE_PRIMARY, ProjectFileDownload.PROJECT_FILE_DOWNLOAD, "PROJECT_FILE_DOWNLOAD_ibfk_1", ProjectFileDownload.PROJECT_FILE_DOWNLOAD.PROJECT_FILE_ID);
        public static final ForeignKey<ProjectFileGameVersionRecord, ProjectFileRecord> PROJECT_FILE_GAME_VERSION_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_FILE_PRIMARY, ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION, "PROJECT_FILE_GAME_VERSION_ibfk_1", ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_FILE_ID);
        public static final ForeignKey<ProjectFileGameVersionRecord, GameVersionRecord> PROJECT_FILE_GAME_VERSION_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_GAME_VERSION_PRIMARY, ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION, "PROJECT_FILE_GAME_VERSION_ibfk_2", ProjectFileGameVersion.PROJECT_FILE_GAME_VERSION.PROJECT_VERSION_ID);
        public static final ForeignKey<ProjectFileProcessingRecord, ProjectFileRecord> PROJECT_FILE_PROCESSING_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_FILE_PRIMARY, ProjectFileProcessing.PROJECT_FILE_PROCESSING, "PROJECT_FILE_PROCESSING_ibfk_1", ProjectFileProcessing.PROJECT_FILE_PROCESSING.PROJECT_FILE_ID);
        public static final ForeignKey<ProjectMemberRecord, ProjectRecord> PROJECT_MEMBER_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, ProjectMember.PROJECT_MEMBER, "PROJECT_MEMBER_ibfk_2", ProjectMember.PROJECT_MEMBER.PROJECT_ID);
        public static final ForeignKey<ProjectMemberRecord, UserRecord> PROJECT_MEMBER_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, ProjectMember.PROJECT_MEMBER, "PROJECT_MEMBER_ibfk_1", ProjectMember.PROJECT_MEMBER.USER_ID);
        public static final ForeignKey<ProjectTypeRecord, GameRecord> PROJECT_TYPE_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_GAME_PRIMARY, ProjectType.PROJECT_TYPE, "PROJECT_TYPE_ibfk_1", ProjectType.PROJECT_TYPE.GAME_ID);
        public static final ForeignKey<ProjectTypeCategoryRecord, ProjectTypeRecord> PROJECT_TYPE_CATEGORY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_TYPE_PRIMARY, ProjectTypeCategory.PROJECT_TYPE_CATEGORY, "PROJECT_TYPE_CATEGORY_ibfk_1", ProjectTypeCategory.PROJECT_TYPE_CATEGORY.PROJECT_TYPE_ID);
        public static final ForeignKey<ProjectTypePermissionRecord, ProjectTypeRecord> PROJECT_TYPE_PERMISSION_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_TYPE_PRIMARY, ProjectTypePermission.PROJECT_TYPE_PERMISSION, "PROJECT_TYPE_PERMISSION_ibfk_1", ProjectTypePermission.PROJECT_TYPE_PERMISSION.ID);
        public static final ForeignKey<UserBetaKeyRecord, UserRecord> USER_BETA_KEY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, UserBetaKey.USER_BETA_KEY, "USER_BETA_KEY_ibfk_1", UserBetaKey.USER_BETA_KEY.USER_ID);
        public static final ForeignKey<UserBetaKeyRecord, UserRecord> USER_BETA_KEY_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, UserBetaKey.USER_BETA_KEY, "USER_BETA_KEY_ibfk_2", UserBetaKey.USER_BETA_KEY.CREATION_USER_ID);
    }
}
