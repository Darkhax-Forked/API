var database = require('../database');
var Sequelize = require('sequelize');
var sequelize = database.sequelize;
var OAuthRefreshToken = sequelize.define('OAUTH_REFRESH_TOKEN', {
    refresh_token: {
        type: Sequelize.STRING,
        field: 'REFRESH_TOKEN'
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
        type: Sequelize.STRING,
        field: 'CLIENT_ID'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

module.exports = OAuthRefreshToken;