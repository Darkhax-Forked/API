var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var database = require('./database');

module.exports = {
    route: function (router) {
        router.route('/users').get(function (req, res) {
            database.User.findAll().then(function (users) {
                res.json(users);
            });
        });
        router.get('/users/:id', function (req, res) {
            database.User.findOne({where: {id: req.params.id}}).then(function (user) {
                if (user) {
                    res.json(user);
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'User Not Found'});
                }
            });
        });
    }
};