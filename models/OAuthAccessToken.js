var database = require('../database');
var Sequelize = require('sequelize');
var sequelize = database.sequelize;
var OAuthAccessToken = sequelize.define('OAUTH_ACCESS_TOKEN', {
    access_token: {
        type: Sequelize.STRING,
        field: 'ACCESS_TOKEN'
    },
    expires: {
        type: Sequelize.DATE,
        field: 'EXPIRES'
    },
    scope: {
        type: Sequelize.STRING,
        field: 'SCOPE'
    },
    user_id: {
        type: Sequelize.INTEGER, //Links to USER_ID in USER
        field: 'USER_ID'
    },
    client_id: {
        type: Sequelize.STRING, //Links to CLIENT_ID in OAUTH_CLIENT
        field: 'CLIENT_ID'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

module.exports = OAuthAccessToken;