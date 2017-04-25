const fileUpload = require('express-fileupload');
const sharp = require('sharp');
const randomstring = require('randomstring');
const fsp = require('fs-promise');
const crypto = require('crypto');

const AVATAR_SIZE = 400;

function getUserAvatarUrl(user) {
    if (user.avatar !== null) {
        return `/avatars/${user.avatar}`;
    }
    const md5 = crypto.createHash('md5').update(user.email.trim().toLowerCase()).digest('hex');
    return `https://www.gravatar.com/avatar/${md5}?d=monsterid`;
}

function getAvatarResource(name) {
    return `${__dirname}/public/avatars/${name}`;
}

// Sets the user's avatar and deletes the old one
// Returns a promise containing the resulting avatar URL
// sharpImage may be null
function setUserAvatar(user, sharpImage) {
    let promise;

    if (sharpImage !== null) {
        // Creates 32 alphanumeric random characters
        const avatar = `${randomstring.generate()}.png`;

        // TODO: Set up upload to CDN instead of local
        promise = sharpImage.toFile(getAvatarResource(avatar))
        .then(() => avatar);
    } else {
        promise = Promise.resolve(null);
    }

    // If the user already has an avatar, try to delete it
    if (user.avatar !== null) {
        // The file may not exist due to a manual deletion. Carry on as normal.
        promise = promise.then(avatar => fsp.unlink(getAvatarResource(user.avatar))
            .then(() => avatar, () => avatar));
    }

    return promise.then(avatar => user.update({ avatar }))
    .then(() => getUserAvatarUrl(user));
}

module.exports = {
    route(router, database) {
        const mapUserProps = user => ({
            id: user.id,
            username: user.username,
            avatar: getUserAvatarUrl(user),
            createdAt: user.createdAt,
        });

        router.get('/users', (req, res) => {
            let where = {};
            if (req.query.username) {
                where.username = req.query.username;
                database.User.findOne({
                    attributes: ['id', 'username', 'avatar', 'createdAt'],
                    where: where
                }).then((user) => {
                    if (user) {
                        res.json(user);
                    } else {
                        res.status(404).json({status: 404, error: 'Not Found', message: 'User Not Found'});
                    }
                });

            } else {
                database.User.findAll({attributes: ['id', 'username', 'avatar', 'createdAt']}).then((users) => {
                    res.json(users);
                });
            }
        });

        const loadUser = (req, res, next) => {
            database.User.findById(req.params.userId).then((user) => {
                if (user) {
                    req.user = user;
                    next();
                } else {
                    res.status(404).json({ status: 404, error: 'Not Found', message: 'User Not Found' });
                }
            });
        };

        router.get('/users/:userId', loadUser, (req, res) => res.json(mapUserProps(req.user)));

        router.post('/users/:userId/avatar', loadUser, fileUpload({
            limits: { fileSize: 1024 * 1024 },
        }), (req, res) => {
            if (req.files) {
                const file = req.files.file;

                try {
                    const sharpImage = sharp(file.data).resize(AVATAR_SIZE, AVATAR_SIZE);

                    setUserAvatar(req.user, sharpImage)
                    .then((avatarUrl) => {
                        console.log(avatarUrl);
                        res.redirect(303, avatarUrl);
                    }, (error) => {
                        res.status(500).json({ status: 500, error: 'Internal Server Error', message: error.message });
                    });
                } catch (error) {
                    res.status(400).json({ status: 400, error: 'Bad Request', message: 'Bad image format' });
                }
            } else {
                res.status(400).json({ status: 400, error: 'Bad Request', message: 'File not attached' });
            }
        });

        router.delete('/users/:userId/avatar', loadUser, (req, res) => {
            setUserAvatar(req.user, null)
            .then((avatarUrl) => {
                res.redirect(303, avatarUrl);
            }, (error) => {
                res.status(500).json({ status: 500, error: 'Internal Server Error', message: error.message });
            });
        });
    },
};
