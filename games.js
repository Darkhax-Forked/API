module.exports = {
    route(router, database) {
        router.get('/games', (req, res) => {
            database.Game.findAll().then((games) => {
                res.json(games);
            });
        });

        const loadGame = (req, res, next) => {
            database.Game.findById(req.params.gameId).then((game) => {
                if (game) {
                    req.game = game;
                    next();
                } else {
                    res.status(404).json({ status: 404, error: 'Not Found', message: 'Game Not Found' });
                }
            });
        };

        router.get('/games/:gameId', loadGame, (req, res) => res.json(req.game));

        router.get('/games/:gameId/projectTypes', loadGame, (req, res) => {
            req.game.getProjectTypes().then((projectTypes) => {
                res.json(projectTypes);
            });
        });

        // TODO: Add get for game projects
    },
};
