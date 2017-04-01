var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var database = require('./database');

module.exports = {
    route: function (router) {

        router.get('/projects', function (req, res) {
            database.Project.findAll({where: {}}).then(function (projects) {
                res.json(projects);
            });
        });

        router.get('/projects/:id', function (req, res) {
            database.Project.findOne({where: {id: req.params.id}}).then(function (project) {
                if (project) {
                    res.json(project);
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Project Not Found'});
                }
            });
        });

        router.get('/projects/:id/authors', function (req, res) {
            database.Project.findOne({where: {id: req.params.id}}).then(function (project) {
                if (project) {
                    database.ProjectAuthors.findAll({where: {project_id: req.params.id}}).then(function (authors) {
                        res.json(authors);
                    });
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Project Not Found'});
                }
            });
        });

        router.get('/projects/:id/files', function (req, res) {
            database.Project.findOne({where: {id: req.params.id}}).then(function (project) {
                if (project) {
                    database.ProjectFiles.findAll({where: {project_id: req.params.id}}).then(function (files) {
                        res.json(files);
                    });
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Project Not Found'});
                }
            });
        });

        router.get('/projects/:id/categories', function (req, res) {
            database.Project.findOne({where: {id: req.params.id}}).then(function (project) {
                if (project) {
                    database.ProjectCategories.findAll({where: {project_id: req.params.id}}).then(function (categories) {
                        res.json(categories);
                    });
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'Project Not Found'});
                }
            });
        });
    }
};