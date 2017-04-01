var express = require('express');
var bodyParser = require('body-parser');
var promise = require('promise');
var database = require('./database');
var oauth = require('./oauth');
var users = require('./users');
var projects = require('./projects');
var games = require('./games');
var passport = require('passport');

var app = express();
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.cookieParser());
app.use(bodyParser.json());

var httpPort = 1234;

var v1router = express.Router();
var oauthRouter = express.Router();

v1router.get('/', function (req, res) {
    res.json({status: 404, error: 'Not Found', message: 'No such endpoint.'})
});

users.route(v1router);
projects.route(v1router);
games.route(v1router);
oauth.route(oauthRouter)

app.use('/v1', v1router);
app.use('/oauth', oauthRouter);

app.listen(httpPort);

console.log('Magic happens on port ' + httpPort);
