const express = require('express');
const Database = require('./database');
const oauth = require('./oauth');
const users = require('./users');
const projects = require('./projects');
const games = require('./games');
const projectTypes = require('./project-types');

const app = express();

const httpPort = 3000;

Database.then((database) => {
    const v1router = express.Router();
    const oauthRouter = express.Router();

    [users, projects, games, projectTypes].forEach(it => it.route(v1router, database));
    oauth.route(oauthRouter);

    app.use('/v1', v1router);
    app.use('/oauth', oauthRouter);
    /* app.oauth = oauthserver({
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
    /* app.oauth= new OAuthServer({
        model: authModel
    });
    app.all('/oauth/token', app.oauth.grant());
    app.use(app.oauth.errorHandler());
    app.use(app.oauth.authorise());*/

    app.get('/', (req, res) => {
        res.send('Secret area');
    });

    v1router.get('*', (req, res) => {
        res.status(404);
        res.json({ status: 404, error: 'Not Found', message: 'No such endpoint.' });
    });

    app.all('/ping', (req, res) => {
        res.send('pong');
    });

    app.listen(httpPort);

    console.log(`Magic happens on port ${httpPort}`);
});
