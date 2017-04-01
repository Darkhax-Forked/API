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
var connection = mysql.createConnection({
    host     : settings.host,
    user     : settings.user,
    password : settings.password,
    database : settings.database
});

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

connection.connect();
exports.connection = connection;

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

var port = 1234;

var router = express.Router();

router.get("/", function (req, res) {
    res.json({status: 404, error: "Not Found", message: "No version specified."})
})

router.get("/v1/", function (req, res) {
    res.json({status: 404, error: "Not Found", message: "No such endpoint."})
})

router.get('/v1/projects', function (req, res) {
    connection.query('SELECT * FROM `PROJECT`', function (error, results, fields) {
        if (error) res.json({status: 0, error: "", message: ""});
        res.json(results.map(function(result) {
            return {
                id:result.PROJECT_ID,
                game:result.GAME_ID,
                type:result.TYPE_ID,
                owner:result.USER_ID,
                description:result.DESCRIPTION,
                created:result.CREATED,
                updated:result.LAST_UPDATED
            };
        }));
    });
})

router.get('/v1/projects/:id', function (req, res) {
    connection.query('SELECT * FROM `PROJECT` WHERE `PROJECT_ID` = ' + connection.escape(req.params.id), function (error, results, fields) {
        if (error) res.json({status: 0, error: "", message: ""});
        if(results.length>0) {
            res.json({
                id:result.PROJECT_ID,
                game:result.GAME_ID,
                type:result.TYPE_ID,
                owner:result.USER_ID,
                description:result.DESCRIPTION,
                created:result.CREATED,
                updated         :result.LAST_UPDATED
            });
        } else {
            res.json({status: 404, error: "Not Found", message: "Project Not Found"});
        }
    });
})

router.get('/v1/users', function (req, res) {
    User.findAll().then(function(users) {
        res.json(users);
    })
})

router.get('/v1/users/:id', function (req, res) {
    User.findOne({where: {id: req.params.id}}).then(function (user) {
        if(user) {
            res.json(user);
        } else {
            res.json({status: 404, error: "Not Found", message: "User Not Found"});
        }
    })
})


app.use("/api", router);

app.listen(port);

console.log("Magic happens on port " + port);