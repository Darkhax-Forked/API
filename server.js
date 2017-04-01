var express = require('express');
var bodyParser = require('body-parser');
var promise = require('promise');
var oauthserver = require('oauth2-server');
var database = require('./database');

var app = express();
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

var httpPort = 1234;

var v1router = express.Router();

v1router.get('/', function (req, res) {
    res.json({status: 404, error: 'Not Found', message: 'No such endpoint.'})
});

v1router.get('/projects', function (req, res) {
    database.Project.findAll({where:{}}).then(function(projects) {
        res.json(projects);
    });
});

v1router.get('/projects/:id', function (req, res) {
    database.Project.findOne({where: {id: req.params.id}}).then(function (project) {
        if(project) {
            res.json(project);
        } else {
            res.status(404);
            res.json({status: 404, error: 'Not Found', message: 'Project Not Found'});
        }
    });
});

v1router.get('/projects/:id/authors', function (req, res) {
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

v1router.get('/projects/:id/files', function (req, res) {
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

v1router.get('/projects/:id/categories', function (req, res) {
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

v1router.get('/users', function (req, res) {
    database.User.findAll().then(function(users) {
        res.json(users);
    });
});

v1router.get('/games', function (req, res) {
    database.Game.findAll().then(function(games) {
        res.json(games);
    });
});

v1router.get('/games/:id', function (req, res) {
    database.Game.findOne({where: {id: req.params.id}}).then(function (game) {
        if(game) {
            res.json(game);
        } else {
            res.status(404);
            res.json({status: 404, error: 'Not Found', message: 'Game Not Found'});
        }
    });
});

v1router.get('/games/:id/projects', function (req, res) {
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

v1router.get('/users/:id', function (req, res) {
    database.User.findOne({where: {id: req.params.id}}).then(function (user) {
        if(user) {
            res.json(user);
        } else {
            res.status(404);
            res.json({status: 404, error: 'Not Found', message: 'User Not Found'});
        }
    });
});


app.use('/v1', v1router);

app.listen(httpPort);

console.log('Magic happens on port ' + httpPort);
