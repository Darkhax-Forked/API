var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');
var promise = require('promise');
var oauthserver = require('oauth2-server');

var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'mmdsite',
    password : 'hah',
    database : 'mmdsite'
});

connection.connect();
exports.connection = connection;

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

var port = 8080;

var router = express.Router();

router.get('/', function (req, res) {
    res.json({status: 404, error: "Not Found", message: "No version specified."})
})

router.get('/v1/', function (req, res) {
    res.json({status: 404, error: "Not Found", message: "No such endpoint."})
})

router.get('/v1/users', function (req, res) {

    res.json({status: 404, error: "Not Found", message: ""})
})

router.get('/v1/users/:id', function (req, res) {
    res.json({status: 404, error: "Not Found", message: req.params.id})
})

app.use('/api', router);

app.listen(port);

console.log('Magic happens on port ' + port);