var database = require('../database');
var Sequelize = require('sequelize');
var sequelize = database.sequelize;
var OAuthClient = sequelize.define('OAUTH_CLIENT', {
    client_id: {
        type: Sequelize.STRING,
        field: 'CLIENT_ID'
    },
    user: {
        type: Sequelize.STRING,
        field: 'USER_ID' //Links to USER_ID in USER
    },
    client_secret: {
        type: Sequelize.STRING,
        field: 'CLIENT_SECRET'
    },
    redirect_uri: {
        type: Sequelize.STRING,
        field: 'REDIRECT_URI'
    },
    grant_types: {
        type: Sequelize.STRING,
        field: 'USER_ID'
    },
    scope: {
        type: Sequelize.STRING,
        field: 'SCOPE'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

module.exports = OAuthClient;