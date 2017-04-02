var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var database = require('./database');

module.exports = {
    route: function (router) {

        router.get('/games', function (req, res) {
            database.Game.findAll().then(function(games) {
                res.json(games);
            });
        });

        router.get('/games/:id', function (req, res) {
            database.Game.findOne({where: {id: req.params.id}}).then(function (game) {
                if(game) {
                    res.json(game);
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Game Not Found'});
                }
            });
        });

        router.get('/games/:id/projects', function (req, res) {
            database.Game.findOne({where: {id: req.params.id}}).then(function (game) {
                if(game) {
                    database.Project.findAll({where: {game: req.params.id}}).then(function (projects) {
                        res.json(projects);
                    });
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Game Not Found'});
                }
            });
        });
    }
};