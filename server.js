var express = require("express");
var app = express();
var fs = require("fs");
var bodyParser = require("body-parser");
var mysql = require("mysql");
var promise = require("promise");
var Sequelize = require('sequelize');
var oauthserver = require("oauth2-server");

var settings = JSON.parse(fs.readFileSync("../settings.json"));
var sequelize = new Sequelize(settings.database, settings.user, settings.password,
{
    host: settings.host,
    dialect: 'mariadb'
}
);

var User = sequelize.define('USERS', {
    id: {
        type: Sequelize.INTEGER,
        field: "USER_ID",
        primaryKey: true
    },
    username: {
        type: Sequelize.STRING,
        field: "USERNAME"
    },
    created: {
        type: Sequelize.DATE,
        field: "CREATED"
    },
    avatar: {
        type: Sequelize.STRING,
        field: "AVATAR"
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
        field: "GAME_ID",
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        field: "NAME"
    },
    website: {
        type: Sequelize.STRING,
        field: "WEBSITE"
    },
    description: {
        type: Sequelize.STRING,
        field: "DESCRIPTION"
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
        field: "PROJECT_ID",
        primaryKey: true
    },
    user_id: {
        type: Sequelize.INTEGER,
        field: "USER_ID"
    },
    role: {
        type: Sequelize.STRING,
        field: "ROLE"
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
        field: "PROJECT_ID",
        primaryKey: true
    },
    category: {
        type: Sequelize.STRING,
        field: "CATEGORY"
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
        field: "TYPE_ID",
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        field: "TYPE_NAME"
    },
    description: {
        type: Sequelize.STRING,
        field: "DESCRIPTION"
    },
    game: {
        type: Sequelize.INTEGER,
        field: "GAME_ID"
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
        field: "PROJECT_ID",
        primaryKey: true
    },
    sha256: {
        type: Sequelize.STRING,
        field: "SHA256"
    },
    file_name: {
        type: Sequelize.STRING,
        field: "ORIGINAL_FILE_NAME"
    },
    display_name: {
        type: Sequelize.STRING,
        field: "FILE_NAME"
    },
    release_type: {
        type: Sequelize.STRING,
        field: "RELEASE_TYPE"
    },
    release_date: {
        type: Sequelize.DATE,
        field: "RELEASE_DATE"
    },
    downloads: {
        type: Sequelize.INTEGER,
        field: "DOWNLOADS"
    },
    size: {
        type: Sequelize.INTEGER,
        field: "SIZE"
    },
    changelog: {
        type: Sequelize.STRING,
        field: "CHANGELOG"
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
        field: "PROJECT_ID",
        primaryKey: true
    },
    game: {
        type: Sequelize.INTEGER,
        field: "GAME_ID"
    },
    type: {
        type: Sequelize.INTEGER,
        field: "TYPE_ID"
    },
    name: {
        type: Sequelize.STRING,
        field: "PROJECT_NAME"
    },
    owner: {
        type: Sequelize.INTEGER,
        field: "USER_ID"
    },
    description: {
        type: Sequelize.STRING,
        field: "DESCRIPTION"
    },
    created: {
        type: Sequelize.DATE,
        field: "CREATED"
    },
    updated: {
        type: Sequelize.DATE,
        field: "LAST_UPDATED"
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

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

var port = 1234;

var v1router = express.Router();

v1router.get("/", function (req, res) {
    res.json({status: 404, error: "Not Found", message: "No such endpoint."})
});

v1router.get('/projects', function (req, res) {
    Promise.all(Project.findAll())
    Project.findAll({where:{}}).then(function(projects) {
        res.json(projects);
    });
});

v1router.get('/projects/:id', function (req, res) {
    Project.findOne({where: {id: req.params.id}}).then(function (project) {
        if(project) {
            res.json(project);
        } else {
            res.json({status: 404, error: "Not Found", message: "Project Not Found"});
        }
    })
});

v1router.get('/projects/:id/authors', function (req, res) {
    ProjectAuthors.findAll({where: {project_id: req.params.id}}).then(function (authors) {
        if(authors) {
            res.json(authors);
        } else {
            res.json({status: 404, error: "Not Found", message: "No Authors Found"});
        }
    })
});

v1router.get('/projects/:id/files', function (req, res) {
    ProjectFiles.findAll({where: {project_id: req.params.id}}).then(function (files) {
        if(files) {
            res.json(files);
        } else {
            res.json({status: 404, error: "Not Found", message: "No Files Found"});
        }
    })
});

v1router.get('/projects/:id/categories', function (req, res) {
    ProjectCategories.findAll({where: {project_id: req.params.id}}).then(function (categories) {
        if(categories) {
            res.json(categories);
        } else {
            res.json({status: 404, error: "Not Found", message: "No Categories Found"});
        }
    })
});

v1router.get('/users', function (req, res) {
    User.findAll().then(function(users) {
        res.json(users);
    })
});

v1router.get('/users/:id', function (req, res) {
    User.findOne({where: {id: req.params.id}}).then(function (user) {
        if(user) {
            res.json(user);
        } else {
            res.json({status: 404, error: "Not Found", message: "User Not Found"});
        }
    })
});


app.use("/v1", v1router);

app.listen(port);

console.log("Magic happens on port " + port);