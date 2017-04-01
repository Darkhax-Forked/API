var settings = require('./settings');
var Sequelize = require('sequelize');

var sequelize = new Sequelize(settings.database, settings.user, settings.password, {
    host: settings.host,
    dialect: 'mariadb',
    port: settings.port
});

var User = sequelize.define('USERS', {
    id: {
        type: Sequelize.INTEGER,
        field: 'USER_ID',
        primaryKey: true
    },
    username: {
        type: Sequelize.STRING,
        field: 'USERNAME'
    },
    created: {
        type: Sequelize.DATE,
        field: 'CREATED'
    },
    avatar: {
        type: Sequelize.STRING,
        field: 'AVATAR'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var Game = sequelize.define('GAME', {
    id: {
        type: Sequelize.INTEGER,
        field: 'GAME_ID',
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        field: 'NAME'
    },
    website: {
        type: Sequelize.STRING,
        field: 'WEBSITE'
    },
    description: {
        type: Sequelize.STRING,
        field: 'DESCRIPTION'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var ProjectAuthors = sequelize.define('PROJECT_AUTHORS', {
    project_id: {
        type: Sequelize.INTEGER,
        field: 'PROJECT_ID',
        primaryKey: true
    },
    user_id: {
        type: Sequelize.INTEGER,
        field: 'USER_ID'
    },
    role: {
        type: Sequelize.STRING,
        field: 'ROLE'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var ProjectCategories = sequelize.define('PROJECT_CATEGORIES', {
    id: {
        type: Sequelize.INTEGER,
        field: 'PROJECT_ID',
        primaryKey: true
    },
    category: {
        type: Sequelize.STRING,
        field: 'CATEGORY'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var ProjectType = sequelize.define('PROJECT_TYPE', {
    id: {
        type: Sequelize.INTEGER,
        field: 'TYPE_ID',
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        field: 'TYPE_NAME'
    },
    description: {
        type: Sequelize.STRING,
        field: 'DESCRIPTION'
    },
    game: {
        type: Sequelize.INTEGER,
        field: 'GAME_ID'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var ProjectFiles = sequelize.define('PROJECT_FILES', {
    project_id: {
        type: Sequelize.INTEGER,
        field: 'PROJECT_ID',
        primaryKey: true
    },
    sha256: {
        type: Sequelize.STRING,
        field: 'SHA256'
    },
    file_name: {
        type: Sequelize.STRING,
        field: 'ORIGINAL_FILE_NAME'
    },
    display_name: {
        type: Sequelize.STRING,
        field: 'FILE_NAME'
    },
    release_type: {
        type: Sequelize.STRING,
        field: 'RELEASE_TYPE'
    },
    release_date: {
        type: Sequelize.DATE,
        field: 'RELEASE_DATE'
    },
    downloads: {
        type: Sequelize.INTEGER,
        field: 'DOWNLOADS'
    },
    size: {
        type: Sequelize.INTEGER,
        field: 'SIZE'
    },
    changelog: {
        type: Sequelize.STRING,
        field: 'CHANGELOG'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

var Project = sequelize.define('PROJECT', {
    id: {
        type: Sequelize.INTEGER,
        field: 'PROJECT_ID',
        primaryKey: true
    },
    game: {
        type: Sequelize.INTEGER,
        field: 'GAME_ID'
    },
    type: {
        type: Sequelize.INTEGER,
        field: 'TYPE_ID'
    },
    name: {
        type: Sequelize.STRING,
        field: 'PROJECT_NAME'
    },
    owner: {
        type: Sequelize.INTEGER,
        field: 'USER_ID'
    },
    description: {
        type: Sequelize.STRING,
        field: 'DESCRIPTION'
    },
    created: {
        type: Sequelize.DATE,
        field: 'CREATED'
    },
    updated: {
        type: Sequelize.DATE,
        field: 'LAST_UPDATED'
    },
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

exports.sequelize = sequelize;
exports.Game = Game;
exports.Project = Project;
exports.User = User;
exports.ProjectFiles = ProjectFiles;