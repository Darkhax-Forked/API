module.exports = {
    route(router, database) {
        router.get('/projectTypes', (req, res) => {
            database.ProjectType.findAll().then((projectTypes) => {
                res.json(projectTypes);
            });
        });

        const loadProjectType = (req, res, next) => {
            database.ProjectType.findById(req.params.projectTypeId).then((projectType) => {
                if (projectType) {
                    req.projectType = projectType;
                    next();
                } else {
                    res.status(404).json({ status: 404, error: 'Not Found', message: 'Project Type Not Found' });
                }
            });
        };

        router.get('/projectTypes/:projectTypeId', loadProjectType, (req, res) => res.json(req.projectType));

        router.get('/projectTypes/:projectTypeId/projects', loadProjectType, (req, res) => {
            req.projectType.getProjects().then((projects) => {
                res.json(projects);
            });
        });
        router.get('/projectTypes/:projectTypeId/categories', (req, res) => {
            database.ProjectTypeCategory.findAll({where:{projectTypeId:req.params.projectTypeId}}).then((projectType) => {
                if (projectType) {
                    res.json(projectType);
                } else {
                    res.status(404).json({ status: 404, error: 'Not Found', message: 'Project Type Not Found' });
                }
            });
        });
    },
};
