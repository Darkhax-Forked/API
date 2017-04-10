module.exports = {
    route(router, database) {
        const mapUserProps = user => ({
            id: user.id,
            username: user.username,
            avatar: getUserAvatarUrl(user),
            createdAt: user.createdAt,
        });
        router.get('/projects', (req, res) => {
            database.Project.findAll().then((projects) => {
                res.json(projects);
            });
        });

        const loadProject = (req, res, next) => {
            database.Project.findById(req.params.projectId).then((project) => {
                if (project) {
                    req.project = project;
                    next();
                } else {
                    res.status(404).json({ status: 404, error: 'Not Found', message: 'Project Not Found' });
                }
            });
        };

        router.get('/projects/:projectId', loadProject, (req, res) => res.json(req.project));

        router.get('/projects/:projectId/authors', loadProject, (req, res) => {
            req.project.getUsers().then((users) => {
                res.json(users.map(mapUserProps));
            });
        });

        router.get('/projects/:projectId/files', loadProject, (req, res) => {
            req.project.getProjectFiles().then((projectFiles) => {
                res.json(projectFiles);
            });
        });

        router.get('/projects/:projectId/categories', loadProject, (req, res) => {
            req.project.getProjectCategories().then((projectCategories) => {
                res.json(projectCategories);
            });
        });
    },
};
