CREATE TABLE game (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  name        VARCHAR(200)  NOT NULL,
  website     VARCHAR(300)  NOT NULL,
  description VARCHAR(2000) NOT NULL,

  slug        VARCHAR(200)  NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE gameVersion (
  id        BIGINT       NOT NULL AUTO_INCREMENT,

  version   VARCHAR(200) NOT NULL,
  website   VARCHAR(300),

  createdAt DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  gameId    BIGINT       NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (gameId) REFERENCES game (id)
);

CREATE TABLE user (
  id            BIGINT       NOT NULL AUTO_INCREMENT,

  email         VARCHAR(255) NOT NULL UNIQUE,
  username      VARCHAR(20)  NOT NULL UNIQUE,
  password      VARCHAR(60)  NOT NULL,

  avatar        VARCHAR(500) NOT NULL,

  verifiedEmail BIT(1)       NOT NULL DEFAULT FALSE,
  mfaEnabled    BIT(1)       NOT NULL DEFAULT FALSE,

  location      VARCHAR(50),
  firstName     VARCHAR(50),
  lastName      VARCHAR(50),

  mfaSecret     VARCHAR(32),

  createdAt     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id)
);

CREATE TABLE authAccessToken (
  token        VARCHAR(255) NOT NULL,
  refreshToken VARCHAR(255) NOT NULL UNIQUE,

  userID       BIGINT       NOT NULL,

  PRIMARY KEY (token),
  FOREIGN KEY (userID) REFERENCES user (id)
);

CREATE TABLE authMFAToken (
  token  VARCHAR(255) NOT NULL,

  userID BIGINT       NOT NULL,
  PRIMARY KEY (token),
  FOREIGN KEY (userID) REFERENCES user (id)
);

CREATE TABLE userBetaKey (
  betaKey        VARCHAR(32) NOT NULL,

  valid          BIT(1)      NOT NULL,
  createdAt      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  userID         BIGINT      NOT NULL,
  creationUserId BIGINT      NOT NULL,
  PRIMARY KEY (betaKey),
  FOREIGN KEY (userID) REFERENCES user (id),
  FOREIGN KEY (creationUserId) REFERENCES user (id)
);


CREATE TABLE projectType (
  id          BIGINT        NOT NULL AUTO_INCREMENT,
  name        VARCHAR(300)  NOT NULL,
  description VARCHAR(2000) NOT NULL,
  slug        VARCHAR(200)  NOT NULL,

  gameId      BIGINT        NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (gameId) REFERENCES game (id)
);

CREATE TABLE project (
  id               BIGINT        NOT NULL AUTO_INCREMENT,
  name             VARCHAR(255)  NOT NULL,
  shortDescription VARCHAR(300)  NOT NULL,
  description      VARCHAR(5000) NOT NULL,
  descriptionType  VARCHAR(255)  NOT NULL,

  slug             VARCHAR(200)  NOT NULL,
  logo             VARCHAR(255)  NOT NULL,
  totalDownloads   BIGINT        NOT NULL DEFAULT FALSE,

  deleted          BIT(1)        NOT NULL DEFAULT FALSE,

  updatedAt        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  createdAt        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

  projectTypeId    BIGINT        NOT NULL,
  userId           BIGINT        NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (projectTypeId) REFERENCES projectType (id),
  FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE projectTypeCategory (
  id            BIGINT        NOT NULL AUTO_INCREMENT,

  name          VARCHAR(255)  NOT NULL,
  description   VARCHAR(2000) NOT NULL,

  slug          VARCHAR(200)  NOT NULL,

  projectTypeId BIGINT        NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (projectTypeId) REFERENCES projectType (id)
);

CREATE TABLE projectCategory (
  projectId             BIGINT NOT NULL,
  projectTypeCategoryId BIGINT NOT NULL,

  PRIMARY KEY (projectId, projectTypeCategoryId),
  FOREIGN KEY (projectId) REFERENCES project (id),
  FOREIGN KEY (projectTypeCategoryId) REFERENCES projectTypeCategory (id)
);

CREATE TABLE projectMember (
  id         BIGINT      NOT NULL AUTO_INCREMENT,

  role       VARCHAR(50) NOT NULL,
  permission BIGINT      NOT NULL,

  createdAt  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

  projectId  BIGINT      NOT NULL,
  userId     BIGINT      NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user (id),
  FOREIGN KEY (projectId) REFERENCES project (id)
);

CREATE TABLE projectFile (
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  sha256      VARCHAR(64)  NOT NULL,

  fileName    VARCHAR(255) NOT NULL,
  displayName VARCHAR(255) NOT NULL,
  size        BIGINT       NOT NULL,

  downloads   BIGINT       NOT NULL DEFAULT 0,
  releaseType VARCHAR(255) NOT NULL,
  status      VARCHAR(255) NOT NULL,
  createdAt   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  parentId    BIGINT,
  projectId   BIGINT       NOT NULL,
  userId      BIGINT       NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (parentId) REFERENCES projectFile (id),
  FOREIGN KEY (projectId) REFERENCES project (id),
  FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE projectFileGameVersion (
  projectFileId    BIGINT NOT NULL,
  projectVersionId BIGINT NOT NULL,

  PRIMARY KEY (projectFileId, projectVersionId),
  FOREIGN KEY (projectFileId) REFERENCES projectFile (id),
  FOREIGN KEY (projectVersionId) REFERENCES gameVersion (id)
);

CREATE TABLE projectFileProcessing (
  projectFileId BIGINT       NOT NULL,
  status        VARCHAR(255) NOT NULL,
  startedAt     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (projectFileId),
  FOREIGN KEY (projectFileId) REFERENCES projectFile (id)
);

CREATE TABLE projectComment (
  id        BIGINT        NOT NULL AUTO_INCREMENT,

  message   VARCHAR(2000) NOT NULL,

  createdAt DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updatedAt DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

  parentId  BIGINT        NOT NULL,
  projectId BIGINT        NOT NULL,
  userId    BIGINT        NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (parentId) REFERENCES projectComment (id),
  FOREIGN KEY (projectId) REFERENCES project (id),
  FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE analyticsAuthAccessToken (
  id           BIGINT       NOT NULL AUTO_INCREMENT,

  token        VARCHAR(255) NOT NULL,
  refreshToken VARCHAR(255) NOT NULL,

  createdAt    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  userId       BIGINT       NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE analyticsAuthMFAToken (
  id        BIGINT       NOT NULL AUTO_INCREMENT,

  token     VARCHAR(255) NOT NULL,
  createdAt DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

  userId    BIGINT       NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user (id)
);

CREATE TABLE analyticsBetaKey (
  userID    BIGINT   NOT NULL,

  createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (userID),
  FOREIGN KEY (userID) REFERENCES user (id)
);