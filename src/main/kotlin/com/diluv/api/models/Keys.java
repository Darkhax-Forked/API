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

    public static final Identity<AnalyticsauthaccesstokenRecord, Long> IDENTITY_ANALYTICSAUTHACCESSTOKEN = Identities0.IDENTITY_ANALYTICSAUTHACCESSTOKEN;
    public static final Identity<AnalyticsauthmfatokenRecord, Long> IDENTITY_ANALYTICSAUTHMFATOKEN = Identities0.IDENTITY_ANALYTICSAUTHMFATOKEN;
    public static final Identity<GameRecord, Long> IDENTITY_GAME = Identities0.IDENTITY_GAME;
    public static final Identity<GameversionRecord, Long> IDENTITY_GAMEVERSION = Identities0.IDENTITY_GAMEVERSION;
    public static final Identity<ProjectRecord, Long> IDENTITY_PROJECT = Identities0.IDENTITY_PROJECT;
    public static final Identity<ProjectcommentRecord, Long> IDENTITY_PROJECTCOMMENT = Identities0.IDENTITY_PROJECTCOMMENT;
    public static final Identity<ProjectfileRecord, Long> IDENTITY_PROJECTFILE = Identities0.IDENTITY_PROJECTFILE;
    public static final Identity<ProjectmemberRecord, Long> IDENTITY_PROJECTMEMBER = Identities0.IDENTITY_PROJECTMEMBER;
    public static final Identity<ProjecttypeRecord, Long> IDENTITY_PROJECTTYPE = Identities0.IDENTITY_PROJECTTYPE;
    public static final Identity<ProjecttypecategoryRecord, Long> IDENTITY_PROJECTTYPECATEGORY = Identities0.IDENTITY_PROJECTTYPECATEGORY;
    public static final Identity<UserRecord, Long> IDENTITY_USER = Identities0.IDENTITY_USER;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AnalyticsauthaccesstokenRecord> KEY_ANALYTICSAUTHACCESSTOKEN_PRIMARY = UniqueKeys0.KEY_ANALYTICSAUTHACCESSTOKEN_PRIMARY;
    public static final UniqueKey<AnalyticsauthmfatokenRecord> KEY_ANALYTICSAUTHMFATOKEN_PRIMARY = UniqueKeys0.KEY_ANALYTICSAUTHMFATOKEN_PRIMARY;
    public static final UniqueKey<AnalyticsbetakeyRecord> KEY_ANALYTICSBETAKEY_PRIMARY = UniqueKeys0.KEY_ANALYTICSBETAKEY_PRIMARY;
    public static final UniqueKey<AuthaccesstokenRecord> KEY_AUTHACCESSTOKEN_PRIMARY = UniqueKeys0.KEY_AUTHACCESSTOKEN_PRIMARY;
    public static final UniqueKey<AuthaccesstokenRecord> KEY_AUTHACCESSTOKEN_REFRESHTOKEN = UniqueKeys0.KEY_AUTHACCESSTOKEN_REFRESHTOKEN;
    public static final UniqueKey<AuthmfatokenRecord> KEY_AUTHMFATOKEN_PRIMARY = UniqueKeys0.KEY_AUTHMFATOKEN_PRIMARY;
    public static final UniqueKey<GameRecord> KEY_GAME_PRIMARY = UniqueKeys0.KEY_GAME_PRIMARY;
    public static final UniqueKey<GameversionRecord> KEY_GAMEVERSION_PRIMARY = UniqueKeys0.KEY_GAMEVERSION_PRIMARY;
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = UniqueKeys0.KEY_PROJECT_PRIMARY;
    public static final UniqueKey<ProjectcategoryRecord> KEY_PROJECTCATEGORY_PRIMARY = UniqueKeys0.KEY_PROJECTCATEGORY_PRIMARY;
    public static final UniqueKey<ProjectcommentRecord> KEY_PROJECTCOMMENT_PRIMARY = UniqueKeys0.KEY_PROJECTCOMMENT_PRIMARY;
    public static final UniqueKey<ProjectfileRecord> KEY_PROJECTFILE_PRIMARY = UniqueKeys0.KEY_PROJECTFILE_PRIMARY;
    public static final UniqueKey<ProjectfilegameversionRecord> KEY_PROJECTFILEGAMEVERSION_PRIMARY = UniqueKeys0.KEY_PROJECTFILEGAMEVERSION_PRIMARY;
    public static final UniqueKey<ProjectfileprocessingRecord> KEY_PROJECTFILEPROCESSING_PRIMARY = UniqueKeys0.KEY_PROJECTFILEPROCESSING_PRIMARY;
    public static final UniqueKey<ProjectmemberRecord> KEY_PROJECTMEMBER_PRIMARY = UniqueKeys0.KEY_PROJECTMEMBER_PRIMARY;
    public static final UniqueKey<ProjecttypeRecord> KEY_PROJECTTYPE_PRIMARY = UniqueKeys0.KEY_PROJECTTYPE_PRIMARY;
    public static final UniqueKey<ProjecttypecategoryRecord> KEY_PROJECTTYPECATEGORY_PRIMARY = UniqueKeys0.KEY_PROJECTTYPECATEGORY_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = UniqueKeys0.KEY_USER_PRIMARY;
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL = UniqueKeys0.KEY_USER_EMAIL;
    public static final UniqueKey<UserRecord> KEY_USER_USERNAME = UniqueKeys0.KEY_USER_USERNAME;
    public static final UniqueKey<UserbetakeyRecord> KEY_USERBETAKEY_PRIMARY = UniqueKeys0.KEY_USERBETAKEY_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<GameversionRecord, GameRecord> GAMEVERSION_IBFK_1 = ForeignKeys0.GAMEVERSION_IBFK_1;
    public static final ForeignKey<ProjectRecord, ProjecttypeRecord> PROJECT_IBFK_1 = ForeignKeys0.PROJECT_IBFK_1;
    public static final ForeignKey<ProjectcategoryRecord, ProjectRecord> PROJECTCATEGORY_IBFK_1 = ForeignKeys0.PROJECTCATEGORY_IBFK_1;
    public static final ForeignKey<ProjectcategoryRecord, ProjecttypecategoryRecord> PROJECTCATEGORY_IBFK_2 = ForeignKeys0.PROJECTCATEGORY_IBFK_2;
    public static final ForeignKey<ProjectcommentRecord, ProjectcommentRecord> PROJECTCOMMENT_IBFK_1 = ForeignKeys0.PROJECTCOMMENT_IBFK_1;
    public static final ForeignKey<ProjectcommentRecord, ProjectRecord> PROJECTCOMMENT_IBFK_2 = ForeignKeys0.PROJECTCOMMENT_IBFK_2;
    public static final ForeignKey<ProjectfileRecord, ProjectfileRecord> PROJECTFILE_IBFK_1 = ForeignKeys0.PROJECTFILE_IBFK_1;
    public static final ForeignKey<ProjectfileRecord, ProjectRecord> PROJECTFILE_IBFK_2 = ForeignKeys0.PROJECTFILE_IBFK_2;
    public static final ForeignKey<ProjectfilegameversionRecord, ProjectfileRecord> PROJECTFILEGAMEVERSION_IBFK_1 = ForeignKeys0.PROJECTFILEGAMEVERSION_IBFK_1;
    public static final ForeignKey<ProjectfilegameversionRecord, GameversionRecord> PROJECTFILEGAMEVERSION_IBFK_2 = ForeignKeys0.PROJECTFILEGAMEVERSION_IBFK_2;
    public static final ForeignKey<ProjectfileprocessingRecord, ProjectfileRecord> PROJECTFILEPROCESSING_IBFK_1 = ForeignKeys0.PROJECTFILEPROCESSING_IBFK_1;
    public static final ForeignKey<ProjectmemberRecord, ProjectRecord> PROJECTMEMBER_IBFK_2 = ForeignKeys0.PROJECTMEMBER_IBFK_2;
    public static final ForeignKey<ProjecttypeRecord, GameRecord> PROJECTTYPE_IBFK_1 = ForeignKeys0.PROJECTTYPE_IBFK_1;
    public static final ForeignKey<ProjecttypecategoryRecord, ProjecttypeRecord> PROJECTTYPECATEGORY_IBFK_1 = ForeignKeys0.PROJECTTYPECATEGORY_IBFK_1;
    public static final ForeignKey<UserbetakeyRecord, UserRecord> USERBETAKEY_IBFK_1 = ForeignKeys0.USERBETAKEY_IBFK_1;
    public static final ForeignKey<UserbetakeyRecord, UserRecord> USERBETAKEY_IBFK_2 = ForeignKeys0.USERBETAKEY_IBFK_2;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<AnalyticsauthaccesstokenRecord, Long> IDENTITY_ANALYTICSAUTHACCESSTOKEN = createIdentity(Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN, Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.ID);
        public static Identity<AnalyticsauthmfatokenRecord, Long> IDENTITY_ANALYTICSAUTHMFATOKEN = createIdentity(Analyticsauthmfatoken.ANALYTICSAUTHMFATOKEN, Analyticsauthmfatoken.ANALYTICSAUTHMFATOKEN.ID);
        public static Identity<GameRecord, Long> IDENTITY_GAME = createIdentity(Game.GAME, Game.GAME.ID);
        public static Identity<GameversionRecord, Long> IDENTITY_GAMEVERSION = createIdentity(Gameversion.GAMEVERSION, Gameversion.GAMEVERSION.ID);
        public static Identity<ProjectRecord, Long> IDENTITY_PROJECT = createIdentity(Project.PROJECT, Project.PROJECT.ID);
        public static Identity<ProjectcommentRecord, Long> IDENTITY_PROJECTCOMMENT = createIdentity(Projectcomment.PROJECTCOMMENT, Projectcomment.PROJECTCOMMENT.ID);
        public static Identity<ProjectfileRecord, Long> IDENTITY_PROJECTFILE = createIdentity(Projectfile.PROJECTFILE, Projectfile.PROJECTFILE.ID);
        public static Identity<ProjectmemberRecord, Long> IDENTITY_PROJECTMEMBER = createIdentity(Projectmember.PROJECTMEMBER, Projectmember.PROJECTMEMBER.ID);
        public static Identity<ProjecttypeRecord, Long> IDENTITY_PROJECTTYPE = createIdentity(Projecttype.PROJECTTYPE, Projecttype.PROJECTTYPE.ID);
        public static Identity<ProjecttypecategoryRecord, Long> IDENTITY_PROJECTTYPECATEGORY = createIdentity(Projecttypecategory.PROJECTTYPECATEGORY, Projecttypecategory.PROJECTTYPECATEGORY.ID);
        public static Identity<UserRecord, Long> IDENTITY_USER = createIdentity(User.USER, User.USER.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<AnalyticsauthaccesstokenRecord> KEY_ANALYTICSAUTHACCESSTOKEN_PRIMARY = createUniqueKey(Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN, "KEY_analyticsAuthAccessToken_PRIMARY", Analyticsauthaccesstoken.ANALYTICSAUTHACCESSTOKEN.ID);
        public static final UniqueKey<AnalyticsauthmfatokenRecord> KEY_ANALYTICSAUTHMFATOKEN_PRIMARY = createUniqueKey(Analyticsauthmfatoken.ANALYTICSAUTHMFATOKEN, "KEY_analyticsAuthMFAToken_PRIMARY", Analyticsauthmfatoken.ANALYTICSAUTHMFATOKEN.ID);
        public static final UniqueKey<AnalyticsbetakeyRecord> KEY_ANALYTICSBETAKEY_PRIMARY = createUniqueKey(Analyticsbetakey.ANALYTICSBETAKEY, "KEY_analyticsBetaKey_PRIMARY", Analyticsbetakey.ANALYTICSBETAKEY.USERID);
        public static final UniqueKey<AuthaccesstokenRecord> KEY_AUTHACCESSTOKEN_PRIMARY = createUniqueKey(Authaccesstoken.AUTHACCESSTOKEN, "KEY_authAccessToken_PRIMARY", Authaccesstoken.AUTHACCESSTOKEN.TOKEN);
        public static final UniqueKey<AuthaccesstokenRecord> KEY_AUTHACCESSTOKEN_REFRESHTOKEN = createUniqueKey(Authaccesstoken.AUTHACCESSTOKEN, "KEY_authAccessToken_refreshToken", Authaccesstoken.AUTHACCESSTOKEN.REFRESHTOKEN);
        public static final UniqueKey<AuthmfatokenRecord> KEY_AUTHMFATOKEN_PRIMARY = createUniqueKey(Authmfatoken.AUTHMFATOKEN, "KEY_authMFAToken_PRIMARY", Authmfatoken.AUTHMFATOKEN.TOKEN);
        public static final UniqueKey<GameRecord> KEY_GAME_PRIMARY = createUniqueKey(Game.GAME, "KEY_game_PRIMARY", Game.GAME.ID);
        public static final UniqueKey<GameversionRecord> KEY_GAMEVERSION_PRIMARY = createUniqueKey(Gameversion.GAMEVERSION, "KEY_gameVersion_PRIMARY", Gameversion.GAMEVERSION.ID);
        public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = createUniqueKey(Project.PROJECT, "KEY_project_PRIMARY", Project.PROJECT.ID);
        public static final UniqueKey<ProjectcategoryRecord> KEY_PROJECTCATEGORY_PRIMARY = createUniqueKey(Projectcategory.PROJECTCATEGORY, "KEY_projectCategory_PRIMARY", Projectcategory.PROJECTCATEGORY.PROJECTID, Projectcategory.PROJECTCATEGORY.PROJECTTYPECATEGORYID);
        public static final UniqueKey<ProjectcommentRecord> KEY_PROJECTCOMMENT_PRIMARY = createUniqueKey(Projectcomment.PROJECTCOMMENT, "KEY_projectComment_PRIMARY", Projectcomment.PROJECTCOMMENT.ID);
        public static final UniqueKey<ProjectfileRecord> KEY_PROJECTFILE_PRIMARY = createUniqueKey(Projectfile.PROJECTFILE, "KEY_projectFile_PRIMARY", Projectfile.PROJECTFILE.ID);
        public static final UniqueKey<ProjectfilegameversionRecord> KEY_PROJECTFILEGAMEVERSION_PRIMARY = createUniqueKey(Projectfilegameversion.PROJECTFILEGAMEVERSION, "KEY_projectFileGameVersion_PRIMARY", Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTFILEID, Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTVERSIONID);
        public static final UniqueKey<ProjectfileprocessingRecord> KEY_PROJECTFILEPROCESSING_PRIMARY = createUniqueKey(Projectfileprocessing.PROJECTFILEPROCESSING, "KEY_projectFileProcessing_PRIMARY", Projectfileprocessing.PROJECTFILEPROCESSING.PROJECTFILEID);
        public static final UniqueKey<ProjectmemberRecord> KEY_PROJECTMEMBER_PRIMARY = createUniqueKey(Projectmember.PROJECTMEMBER, "KEY_projectMember_PRIMARY", Projectmember.PROJECTMEMBER.ID);
        public static final UniqueKey<ProjecttypeRecord> KEY_PROJECTTYPE_PRIMARY = createUniqueKey(Projecttype.PROJECTTYPE, "KEY_projectType_PRIMARY", Projecttype.PROJECTTYPE.ID);
        public static final UniqueKey<ProjecttypecategoryRecord> KEY_PROJECTTYPECATEGORY_PRIMARY = createUniqueKey(Projecttypecategory.PROJECTTYPECATEGORY, "KEY_projectTypeCategory_PRIMARY", Projecttypecategory.PROJECTTYPECATEGORY.ID);
        public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = createUniqueKey(User.USER, "KEY_user_PRIMARY", User.USER.ID);
        public static final UniqueKey<UserRecord> KEY_USER_EMAIL = createUniqueKey(User.USER, "KEY_user_email", User.USER.EMAIL);
        public static final UniqueKey<UserRecord> KEY_USER_USERNAME = createUniqueKey(User.USER, "KEY_user_username", User.USER.USERNAME);
        public static final UniqueKey<UserbetakeyRecord> KEY_USERBETAKEY_PRIMARY = createUniqueKey(Userbetakey.USERBETAKEY, "KEY_userBetaKey_PRIMARY", Userbetakey.USERBETAKEY.BETAKEY);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<GameversionRecord, GameRecord> GAMEVERSION_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_GAME_PRIMARY, Gameversion.GAMEVERSION, "gameVersion_ibfk_1", Gameversion.GAMEVERSION.GAMEID);
        public static final ForeignKey<ProjectRecord, ProjecttypeRecord> PROJECT_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTTYPE_PRIMARY, Project.PROJECT, "project_ibfk_1", Project.PROJECT.PROJECTTYPEID);
        public static final ForeignKey<ProjectcategoryRecord, ProjectRecord> PROJECTCATEGORY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, Projectcategory.PROJECTCATEGORY, "projectCategory_ibfk_1", Projectcategory.PROJECTCATEGORY.PROJECTID);
        public static final ForeignKey<ProjectcategoryRecord, ProjecttypecategoryRecord> PROJECTCATEGORY_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTTYPECATEGORY_PRIMARY, Projectcategory.PROJECTCATEGORY, "projectCategory_ibfk_2", Projectcategory.PROJECTCATEGORY.PROJECTTYPECATEGORYID);
        public static final ForeignKey<ProjectcommentRecord, ProjectcommentRecord> PROJECTCOMMENT_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTCOMMENT_PRIMARY, Projectcomment.PROJECTCOMMENT, "projectComment_ibfk_1", Projectcomment.PROJECTCOMMENT.PARENTID);
        public static final ForeignKey<ProjectcommentRecord, ProjectRecord> PROJECTCOMMENT_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, Projectcomment.PROJECTCOMMENT, "projectComment_ibfk_2", Projectcomment.PROJECTCOMMENT.PROJECTID);
        public static final ForeignKey<ProjectfileRecord, ProjectfileRecord> PROJECTFILE_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTFILE_PRIMARY, Projectfile.PROJECTFILE, "projectFile_ibfk_1", Projectfile.PROJECTFILE.PARENTID);
        public static final ForeignKey<ProjectfileRecord, ProjectRecord> PROJECTFILE_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, Projectfile.PROJECTFILE, "projectFile_ibfk_2", Projectfile.PROJECTFILE.PROJECTID);
        public static final ForeignKey<ProjectfilegameversionRecord, ProjectfileRecord> PROJECTFILEGAMEVERSION_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTFILE_PRIMARY, Projectfilegameversion.PROJECTFILEGAMEVERSION, "projectFileGameVersion_ibfk_1", Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTFILEID);
        public static final ForeignKey<ProjectfilegameversionRecord, GameversionRecord> PROJECTFILEGAMEVERSION_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_GAMEVERSION_PRIMARY, Projectfilegameversion.PROJECTFILEGAMEVERSION, "projectFileGameVersion_ibfk_2", Projectfilegameversion.PROJECTFILEGAMEVERSION.PROJECTVERSIONID);
        public static final ForeignKey<ProjectfileprocessingRecord, ProjectfileRecord> PROJECTFILEPROCESSING_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTFILE_PRIMARY, Projectfileprocessing.PROJECTFILEPROCESSING, "projectFileProcessing_ibfk_1", Projectfileprocessing.PROJECTFILEPROCESSING.PROJECTFILEID);
        public static final ForeignKey<ProjectmemberRecord, ProjectRecord> PROJECTMEMBER_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECT_PRIMARY, Projectmember.PROJECTMEMBER, "projectMember_ibfk_2", Projectmember.PROJECTMEMBER.PROJECTID);
        public static final ForeignKey<ProjecttypeRecord, GameRecord> PROJECTTYPE_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_GAME_PRIMARY, Projecttype.PROJECTTYPE, "projectType_ibfk_1", Projecttype.PROJECTTYPE.GAMEID);
        public static final ForeignKey<ProjecttypecategoryRecord, ProjecttypeRecord> PROJECTTYPECATEGORY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_PROJECTTYPE_PRIMARY, Projecttypecategory.PROJECTTYPECATEGORY, "projectTypeCategory_ibfk_1", Projecttypecategory.PROJECTTYPECATEGORY.PROJECTTYPEID);
        public static final ForeignKey<UserbetakeyRecord, UserRecord> USERBETAKEY_IBFK_1 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, Userbetakey.USERBETAKEY, "userBetaKey_ibfk_1", Userbetakey.USERBETAKEY.USERID);
        public static final ForeignKey<UserbetakeyRecord, UserRecord> USERBETAKEY_IBFK_2 = createForeignKey(com.diluv.api.models.Keys.KEY_USER_PRIMARY, Userbetakey.USERBETAKEY, "userBetaKey_ibfk_2", Userbetakey.USERBETAKEY.CREATIONUSERID);
    }
}