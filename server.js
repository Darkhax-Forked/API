var express = require("express");
var app = express();
var fs = require("fs");
var bodyParser = require("body-parser");
var mysql = require("mysql");
var promise = require("promise");
var oauthserver = require("oauth2-server");

var settings = JSON.parse(fs.readFileSync("../settings.json"));
var connection = mysql.createConnection({
    host     : settings.host,
    user     : settings.user,
    password : settings.password,
    database : settings.database
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

router.get('/v1/users', function (req, res) {
    connection.query('SELECT * FROM `USERS`', function (error, results, fields) {
        if (error) res.json({status: 0, error: "", message: ""});
        res.json(results.map(function(result) {
            return {
                id: result.USER_ID,
                username: result.USERNAME,
                created: result.CREATED,
                avatar: result.AVATAR
            };
        }));
    });
})

router.get('/v1/:id', function (req, res) {
    connection.query('SELECT * FROM `USERS` WHERE `USER_ID` = ' + connection.escape(req.params.id), function (error, results, fields) {
        if (error) res.json({status: 0, error: "", message: ""});
        if(results.length>0) {
            res.json({
                id: results[0].USER_ID,
                username: results[0].USERNAME,
                created: results[0].CREATED,
                avatar: results[0].AVATAR
            });
        } else {
            res.json({status: 404, error: "Not Found", message: "User Not Found"});
        }
    });
})


app.use("/api", router);

app.listen(port);

console.log("Magic happens on port " + port);