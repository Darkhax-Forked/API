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
});

const ProjectAuthor = sequelize.define('projectAuthor', {
    role: {
        type: Sequelize.STRING(50),
        allowNull: false,
    }
});

const ProjectCategory = sequelize.define('projectCategory', {
    projectId: {
        type: Sequelize.INTEGER,
        primaryKey: true,
    },
    category: {
        type: Sequelize.STRING(20),
        primaryKey: true,
    }
}, {
    createdAt: false,
    updatedAt: false,
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
    changelog: Sequelize.TEXT,
});

Game.hasMany(ProjectType, { foreignKey: { allowNull: false } });
ProjectType.hasMany(Project, { foreignKey: { allowNull: false } });
Project.belongsToMany(User, { through: ProjectAuthor, foreignKey: { name: 'projectId', allowNull: false } });
User.belongsToMany(Project, { through: ProjectAuthor, foreignKey: { name: 'userId', allowNull: false } });
Project.hasMany(ProjectFile, { foreignKey: { allowNull: false } });
Project.hasMany(ProjectCategory, { foreignKey: { allowNull: false } });

module.exports = sequelize.sync().then(() => ({
    sequelize,
    Game,
    Project,
    User,
    ProjectFile,
    ProjectAuthor,
    ProjectCategory,
}));
