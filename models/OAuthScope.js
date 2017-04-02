var database = require('../database');
var Sequelize = require('sequelize');
var sequelize = database.sequelize;
var OAuthScope = sequelize.define('OAUTH_REFRESH_TOKEN', {
    scope: {
        type: Sequelize.STRING,
        field: 'SCOPE'
    },
    is_default: {
        type: Sequelize.BOOLEAN,
        field: 'IS_DEFAULT'
    }
}, {
    freezeTableName: true,
    createdAt: false,
    updatedAt: false,
    deletedAt: false
});

module.exports = OAuthScope;