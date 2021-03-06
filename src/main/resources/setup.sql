CREATE TABLE GAME (
  ID          BIGINT        NOT NULL AUTO_INCREMENT,
  NAME        VARCHAR(200)  NOT NULL UNIQUE,
  WEBSITE     VARCHAR(300)  NOT NULL,
  DESCRIPTION VARCHAR(2000) NOT NULL,

  SLUG        VARCHAR(200)  NOT NULL UNIQUE,

  PRIMARY KEY (ID)
);

CREATE TABLE GAME_VERSION (
  ID         BIGINT       NOT NULL AUTO_INCREMENT,

  VERSION    VARCHAR(200) NOT NULL,
  WEBSITE    VARCHAR(300),

  CREATED_AT DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  GAME_ID    BIGINT       NOT NULL,

  PRIMARY KEY (ID),
  FOREIGN KEY (GAME_ID) REFERENCES GAME (ID)
);

CREATE TABLE USER (
  ID             BIGINT       NOT NULL AUTO_INCREMENT,

  EMAIL          VARCHAR(255) NOT NULL UNIQUE,
  USERNAME       VARCHAR(20)  NOT NULL UNIQUE,
  PASSWORD       VARCHAR(60)  NOT NULL,

  PERMISSION     INTEGER      NOT NULL DEFAULT 0,
  AVATAR         VARCHAR(500) NOT NULL,

  VERIFIED_EMAIL BIT(1)       NOT NULL DEFAULT FALSE,
  MFA_ENABLED    BIT(1)       NOT NULL DEFAULT FALSE,

  LOCATION       VARCHAR(50),
  FIRST_NAME     VARCHAR(50),
  LAST_NAME      VARCHAR(50),

  MFA_SECRET     VARCHAR(32),

  CREATED_AT     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (ID)
);

CREATE TABLE AUTH_VERIFY_TOKEN (
  USER_ID BIGINT       NOT NULL,
  TOKEN   VARCHAR(500) NOT NULL,

  PRIMARY KEY (USER_ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

CREATE TABLE AUTH_ACCESS_TOKEN (
  TOKEN         VARCHAR(500) NOT NULL,
  REFRESH_TOKEN VARCHAR(500) NOT NULL UNIQUE,

  USER_ID       BIGINT       NOT NULL,

  PRIMARY KEY (TOKEN),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

CREATE TABLE AUTH_MFA_TOKEN (
  TOKEN   VARCHAR(500) NOT NULL,

  USER_ID BIGINT       NOT NULL,
  PRIMARY KEY (TOKEN),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

CREATE TABLE USER_BETA_KEY (
  BETA_KEY         VARCHAR(32) NOT NULL,

  VALID            BIT(1)      NOT NULL,
  CREATED_AT       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  USER_ID          BIGINT      NOT NULL,
  CREATION_USER_ID BIGINT      NOT NULL,
  PRIMARY KEY (BETA_KEY),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID),
  FOREIGN KEY (CREATION_USER_ID) REFERENCES USER (ID)
);

CREATE TABLE PROJECT_TYPE (
  ID            BIGINT        NOT NULL AUTO_INCREMENT,
  NAME          VARCHAR(300)  NOT NULL,
  DESCRIPTION   VARCHAR(2000) NOT NULL,
  SLUG          VARCHAR(200)  NOT NULL,

  MAX_BYTE_SIZE BIGINT        NOT NULL,

  GAME_ID       BIGINT        NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (GAME_ID) REFERENCES GAME (ID)
);

CREATE TABLE PROJECT_TYPE_PERMISSION (
  ID                BIGINT  NOT NULL AUTO_INCREMENT,

  PERMISSION_CREATE INTEGER NOT NULL,

  PRIMARY KEY (ID),
  FOREIGN KEY (ID) REFERENCES PROJECT_TYPE (ID)
);

CREATE TABLE PROJECT (
  ID                BIGINT        NOT NULL AUTO_INCREMENT,

  NAME              VARCHAR(255)  NOT NULL,
  SHORT_DESCRIPTION VARCHAR(300)  NOT NULL,
  DESCRIPTION       VARCHAR(5000) NOT NULL,
  DESCRIPTION_TYPE  VARCHAR(255)  NOT NULL,

  SLUG              VARCHAR(200)  NOT NULL,
  LOGO              VARCHAR(255)  NOT NULL,
  TOTAL_DOWNLOADS   BIGINT        NOT NULL DEFAULT FALSE,

  DELETED           BIT(1)        NOT NULL DEFAULT FALSE,

  UPDATED_AT        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CREATED_AT        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PROJECT_TYPE_ID   BIGINT        NOT NULL,
  USER_ID           BIGINT        NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (PROJECT_TYPE_ID) REFERENCES PROJECT_TYPE (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

CREATE TABLE PROJECT_TYPE_CATEGORY (
  ID              BIGINT        NOT NULL AUTO_INCREMENT,

  NAME            VARCHAR(255)  NOT NULL,
  DESCRIPTION     VARCHAR(2000) NOT NULL,

  SLUG            VARCHAR(200)  NOT NULL,

  PROJECT_TYPE_ID BIGINT        NOT NULL,

  PRIMARY KEY (ID),
  FOREIGN KEY (PROJECT_TYPE_ID) REFERENCES PROJECT_TYPE (ID)
);

CREATE TABLE PROJECT_CATEGORY (
  PROJECT_ID               BIGINT NOT NULL,
  PROJECT_TYPE_CATEGORY_ID BIGINT NOT NULL,

  PRIMARY KEY (PROJECT_ID, PROJECT_TYPE_CATEGORY_ID),
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (PROJECT_TYPE_CATEGORY_ID) REFERENCES PROJECT_TYPE_CATEGORY (ID)
);

CREATE TABLE PROJECT_MEMBER (
  ID         BIGINT      NOT NULL AUTO_INCREMENT,

  ROLE       VARCHAR(50) NOT NULL,
  PERMISSION INTEGER     NOT NULL,

  CREATED_AT DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PROJECT_ID BIGINT      NOT NULL,
  USER_ID    BIGINT      NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID),
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID)
);

CREATE TABLE PROJECT_FILE (
  ID            BIGINT       NOT NULL AUTO_INCREMENT,
  SHA512        VARCHAR(128),

  FILE_NAME     VARCHAR(255) NOT NULL,
  DISPLAY_NAME  VARCHAR(255) NOT NULL,
  SIZE          BIGINT       NOT NULL,

  DOWNLOADS     BIGINT       NOT NULL DEFAULT 0,
  RELEASE_TYPE  VARCHAR(255) NOT NULL,
  CREATED_AT    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PUBLIC        BIT(1)       NOT NULL DEFAULT FALSE,
  PROCESSED     BIT(1)       NOT NULL DEFAULT FALSE,
  REVIEW_NEEDED BIT(1)       NOT NULL DEFAULT FALSE,

  PARENT_ID     BIGINT,
  PROJECT_ID    BIGINT       NOT NULL,
  USER_ID       BIGINT       NOT NULL,

  PRIMARY KEY (ID),
  FOREIGN KEY (PARENT_ID) REFERENCES PROJECT_FILE (ID),
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);

CREATE TABLE PROJECT_FILE_GAME_VERSION (
  PROJECT_FILE_ID    BIGINT NOT NULL,
  PROJECT_VERSION_ID BIGINT NOT NULL,

  PRIMARY KEY (PROJECT_FILE_ID, PROJECT_VERSION_ID),
  FOREIGN KEY (PROJECT_FILE_ID) REFERENCES PROJECT_FILE (ID),
  FOREIGN KEY (PROJECT_VERSION_ID) REFERENCES GAME_VERSION (ID)
);

CREATE TABLE PROJECT_FILE_PROCESSING (
  PROJECT_FILE_ID BIGINT       NOT NULL,
  STATUS          VARCHAR(200) NOT NULL,

  SUCCESSFUL      BIT(1)       NOT NULL,
  LOGS            LONGTEXT     NOT NULL,
  CREATED_AT      DATETIME     NOT NULL  DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (PROJECT_FILE_ID, STATUS),
  FOREIGN KEY (PROJECT_FILE_ID) REFERENCES PROJECT_FILE (ID)
);

CREATE TABLE PROJECT_FILE_DOWNLOAD (
  PROJECT_FILE_ID BIGINT      NOT NULL,
  IP              VARCHAR(45) NOT NULL,
  CREATED_AT      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (PROJECT_FILE_ID),
  FOREIGN KEY (PROJECT_FILE_ID) REFERENCES PROJECT_FILE (ID)
);

CREATE TABLE PROJECT_COMMENT (
  ID         BIGINT        NOT NULL AUTO_INCREMENT,

  MESSAGE    VARCHAR(2000) NOT NULL,

  CREATED_AT DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATED_AT DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PARENT_ID  BIGINT,
  PROJECT_ID BIGINT        NOT NULL,
  USER_ID    BIGINT        NOT NULL,

  PRIMARY KEY (ID),
  FOREIGN KEY (PARENT_ID) REFERENCES PROJECT_COMMENT (ID),
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (ID),
  FOREIGN KEY (USER_ID) REFERENCES USER (ID)
);