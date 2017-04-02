var express = require('express');
var bodyParser = require('body-parser');
var oauthserver = require('oauth2-server');
var promise = require('promise');
var database = require('./database');
var oauth = require('./oauth');
var users = require('./users');
var projects = require('./projects');
var authModel = require('./models/auth');
var games = require('./games');
var OAuthServer = require('express-oauth-server');

var app = express();
app.use(bodyParser.urlencoded({extended: true}));
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
/*app.oauth = oauthserver({
    model: {
        getClient: function (clientID, clientSecret, callback) {
            console.log(clientID + ":" + clientSecret);
            callback(null, true);
        },
        grantTypeAllowed: function (clientID, grantType, callback) {
            if (grantType === 'password') {
                callback(null, true);
            } else {
                callback('Invalid grant type!', false);
            }
        },
        getUser: function (username, password, callback) {
            if (username == "lclc98") {
                callback(null, true);
            } else {
                callback('Invalid username/password!', false);
            }
        },
        saveAccessToken: function (accessToken, clientId, expires, user, callback) {
            console.log(accessToken + ":" + clientId + ":" + expires + ":" + user);
            callback(null, true);
        },
        saveRefreshToken: function (refreshToken, clientId, expires, user, callback) {
            console.log('test3');
        }
    }, // See below for specification
    grants: ['password'],
    debug: true
});*/
app.oauth= new OAuthServer({
    model: authModel
});
app.all('/oauth/token', app.oauth.grant());
app.use(app.oauth.errorHandler());
app.use(app.oauth.authorise());

app.get('/', function (req, res) {
    res.send('Secret area');
});
app.listen(httpPort);

console.log('Magic happens on port ' + httpPort);
