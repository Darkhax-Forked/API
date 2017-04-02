module.exports = {
    route: (router, database) => {
        router.get('/users', (req, res) => {
            database.User.findAll().then((users) => {
                res.json(users);
            });
        });

        const loadUser = (req, res, next) => {
            database.User.findById(req.params.userId).then((user) => {
                if(user) {
                    req.user = user;
                    next();
                } else {
                    res.status(404);
                    res.json({status: 404, error: 'Not Found', message: 'User Not Found'});
                }
            });
        };

        router.get('/users/:userId', loadUser, (req, res) => res.json(req.user));
    }
};