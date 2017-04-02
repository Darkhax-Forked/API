var database = require("../database");
var sequelize = database.sequelize;

module.exports ={
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
}