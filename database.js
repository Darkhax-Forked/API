const settings = require('./settings');
const Sequelize = require('sequelize');

const sequelize = new Sequelize(settings.database, settings.user, settings.password, {
    host: settings.host,
    dialect: 'mariadb',
    port: settings.port,
    define: {
        freezeTableName: true,
    },
});

const User = sequelize.define('user', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    email: {
        type: Sequelize.STRING(50),
        unique: true,
        allowNull: false,
    },
    username: {
        type: Sequelize.STRING(20),
        unique: true,
        allowNull: false,
    },
    avatar: {
        type: Sequelize.STRING,
        allowNull: false,
    },
    points: {
        type: Sequelize.INTEGER,
        allowNull: false,
    },
    password: {
        type: Sequelize.CHAR(60),
        allowNull: false,
    },
    verifiedEmail: {
        type: Sequelize.BOOLEAN,
        allowNull: false,
    },
    twoFactor: {
        type: Sequelize.BOOLEAN,
        allowNull: false,
    },
    location: {
        type: Sequelize.STRING(50),
        allowNull: true,
    },
    firstName: {
        type: Sequelize.STRING(50),
        allowNull: true,
    },
    lastName: {
        type: Sequelize.STRING(50),
        allowNull: true,
    },
});

const Game = sequelize.define('game', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    name: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    website: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    description: {
        type: Sequelize.TEXT,
        allowNull: false,
    },
}, {
    createdAt: false,
    updatedAt: false,
});

const ProjectType = sequelize.define('projectType', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    name: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    description: {
        type: Sequelize.TEXT,
        allowNull: false,
    },
}, {
    createdAt: false,
    updatedAt: false,
});

const ProjectTypeCategory = sequelize.define('projectTypeCategory', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    name: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    description: {
        type: Sequelize.TEXT,
    },
    icon: {
        type: Sequelize.STRING(50),
    },
}, {
    createdAt: false,
    updatedAt: false,
});

const ProjectCategory = sequelize.define('projectCategory',
    {}, {
        createdAt: false,
        updatedAt: false,
    });

const Project = sequelize.define('project', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    name: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    description: {
        type: Sequelize.TEXT,
    },
    logo: {
        type: Sequelize.STRING,
    },
    totalDownloads: {
        type: Sequelize.INTEGER,
    },
    deleted: {
        type: Sequelize.BOOLEAN,
        allowNull: false,
    },
});

const ProjectMember = sequelize.define('projectMember', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    role: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
});

const ProjectFile = sequelize.define('projectFile', {
    sha256: {
        type: Sequelize.CHAR(64),
        primaryKey: true,
    },
    fileName: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    displayName: {
        type: Sequelize.STRING(50),
        allowNull: false,
    },
    releaseType: {
        type: Sequelize.ENUM('preAlpha', 'alpha', 'beta', 'release'),
        allowNull: false,
    },
    downloads: {
        type: Sequelize.INTEGER,
        allowNull: false,
    },
    size: {
        type: Sequelize.INTEGER,
        allowNull: false,
    },
    changelog: {
        type: Sequelize.TEXT,
        allowNull: true,
    },
});

const ProjectComment = sequelize.define('projectComment', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    message: {
        type: Sequelize.TEXT,
        allowNull: true,
    },
});

const PrivateMessage = sequelize.define('privateMessage', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    message: {
        type: Sequelize.TEXT,
        allowNull: true,
    },
});

const Notification = sequelize.define('notification', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    message: {
        type: Sequelize.TEXT,
        allowNull: true,
    },
    url: {
        type: Sequelize.STRING(50),
        allowNull: true,
    },
});

User.belongsToMany(Project, {through: ProjectMember, foreignKey: {name: 'userId', allowNull: false}});

Game.hasMany(ProjectType, {foreignKey: {allowNull: false}});

Project.belongsTo(Game, {foreignKey: {allowNull: false}});
Project.hasMany(ProjectFile, {foreignKey: {allowNull: false}});
Project.hasMany(ProjectCategory, {foreignKey: {allowNull: false}});
Project.belongsTo(User, {foreignKey: {allowNull: false}});
Project.belongsToMany(User, {through: ProjectMember, foreignKey: {name: 'projectId', allowNull: false}});

ProjectType.hasMany(Project, {foreignKey: {allowNull: false}});

Notification.belongsTo(User, {foreignKey: {name: 'userId', allowNull: false}});

PrivateMessage.belongsTo(User, {foreignKey: {name: 'userId', allowNull: false}});
PrivateMessage.belongsTo(User, {foreignKey: {name: 'fromUserId', allowNull: false}});

ProjectComment.belongsTo(Project, {foreignKey: {allowNull: false}});
ProjectComment.belongsTo(User, {foreignKey: {allowNull: false}});
ProjectComment.belongsTo(ProjectComment, {foreignKey: {allowNull: true}});

ProjectCategory.belongsTo(Project, {foreignKey: {allowNull: false}});
ProjectCategory.belongsTo(ProjectTypeCategory, {foreignKey: {allowNull: false}});

ProjectTypeCategory.belongsTo(ProjectType, {foreignKey: {allowNull: false}});

module.exports = sequelize.sync().then(() => ({
    sequelize,
    User,
    Game,
    ProjectType,
    ProjectTypeCategory,
    ProjectCategory,
    Project,
    ProjectMember,
    ProjectFile,
    ProjectComment,
    PrivateMessage,
    Notification,
}));