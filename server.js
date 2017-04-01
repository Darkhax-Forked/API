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

router.get("/v1/users", function (req, res) {
    connection.query("SELECT * FROM `USERS`", function (error, results, fields) {
        if (error) res.json({status: 0, error: "", message: ""});
        var users = [];
        for (i = 0; i < results.length; i++) {
            var result = results[i];
            var user = {};
            user["id"]=result["USER_ID"];
            user["created"]=result["CREATED"];
            user["avatar"]=result["AVATAR"];
            users[0] = user;
        }
        res.json(users);
    });
})

router.get("/v1/users/:id", function (req, res) {
    res.json({status: 404, error: "Not Found", message: req.params.id})
})

app.use("/api", router);

app.listen(port);

console.log("Magic happens on port " + port);