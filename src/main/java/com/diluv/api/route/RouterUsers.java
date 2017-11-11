package com.diluv.api.route;

import com.diluv.api.DiluvAPI;
import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.models.tables.records.UserBetaKeyRecord;
import com.diluv.api.models.tables.records.UserRecord;
import com.diluv.api.permission.user.UserPermissionType;
import com.diluv.api.utils.AuthorizationUtilities;
import com.diluv.api.utils.ResponseUtilities;
import com.diluv.api.utils.UserUtilities;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.RouterImpl;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.jooq.InsertValuesStep3;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.USER;
import static com.diluv.api.models.Tables.USER_BETA_KEY;


public class RouterUsers extends RouterImpl {

    private final Vertx vertx;

    public RouterUsers(Vertx vertx) {
        super(vertx);
        this.vertx = vertx;

        this.get("/").handler(this::getUsers);
        this.get("/:username").handler(this::getUserByUsername);
        this.get("/:username/settings").handler(this::getUserSettingsByUsername);

        this.post("/:username/security").handler(this::postUserSecurityByUsername);
//        router.post("/users/:username/settings").handler(postUserSettingsByUsername);
        this.post("/generateBetaKey").handler(this::postCreateBetaKeys);
    }


    public void getUsers(RoutingContext event) {
        try {
            List<Long> dbUsers = DiluvAPI.getDSLContext().select(USER.ID)
                    .from(USER)
                    .fetch(0, long.class);

            List<Map<String, Object>> userListOut = new ArrayList<>();
            for (long userId : dbUsers) {
                userListOut.add(UserUtilities.getUserByUserId(userId, false));
            }

            ResponseUtilities.asSuccessResponse(event, userListOut);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

    public void getUserByUsername(RoutingContext event) {
        String username = event.request().getParam("username");

        Long userId = null;
        boolean authorized = false;
        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(token)) {
                JWT jwt = new JWT(token);
                if (!jwt.isExpired()) {
                    String tokenUsername = jwt.getData().getString("username");
                    Long tokenUserId = jwt.getData().getLong("userId");
                    if (tokenUserId != null && (username.equals("me") || username.equals(tokenUsername))) {
                        userId = tokenUserId;
                        authorized = true;
                    }
                }
            }
        }

        if (!authorized) {
            userId = DiluvAPI.getDSLContext().select(USER.ID)
                    .from(USER)
                    .where(USER.USERNAME.eq(username))
                    .fetchOne(0, long.class);
        }

        if (userId != null) {
            ResponseUtilities.asSuccessResponse(event, UserUtilities.getUserByUserId(userId, authorized));
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.USER_NOT_FOUND);
        }
    }

    public void getUserSettingsByUsername(RoutingContext event) {
        String username = event.request().getParam("username");

        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(token)) {
                JWT jwt = new JWT(token);
                if (!jwt.isExpired()) {
                    String tokenUsername = jwt.getData().getString("username");
                    Long tokenUserId = jwt.getData().getLong("userId");
                    if (tokenUserId != null && (username.equals("me") || username.equals(tokenUsername))) {
                        Map<String, Object> userOut = UserUtilities.getUserSettingsByUserId(tokenUserId);
                        ResponseUtilities.asSuccessResponse(event, userOut);
                    }
                }
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.UNAUTHORIZED_REQUEST);
        }
    }

    public void postUserSecurityByUsername(RoutingContext event) {
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);
        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            JWT jwt = new JWT(token);
            if (JWT.isTokenValid(token)) {
                if (!jwt.isExpired()) {
                    Long userId = jwt.getData().getLong("userId");

                    req.endHandler(ctx -> {
                        String oldPassword = req.getFormAttribute("oldPassword");
                        String newPassword = req.getFormAttribute("newPassword");

                        UserRecord user = DiluvAPI.getDSLContext().selectFrom(USER)
                                .where(USER.ID.eq(userId))
                                .fetchAny();
                        if (OpenBSDBCrypt.checkPassword(user.getPassword(), oldPassword.toCharArray())) {
                            byte[] salt = new byte[16];
                            new SecureRandom().nextBytes(salt);
                            String passwordHash = OpenBSDBCrypt.generate(newPassword.toCharArray(), salt, 10);

                            DiluvAPI.getDSLContext().update(USER)
                                    .set(USER.PASSWORD, passwordHash)
                                    .execute();

                            //TODO Proper response
                            Map<String, Object> valid = new HashMap<>();
                            valid.put("valid", true);
                            ResponseUtilities.asSuccessResponse(event, valid);
                        } else {
                            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_PASSWORD_INCORRECT);
                        }
                    });
                } else {
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_TOKEN_EXPIRED);
                }
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_TOKEN_NON_EXIST);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_TOKEN_NULL);
        }
    }

    public void postCreateBetaKeys(RoutingContext event) {
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);
        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            JWT jwt = new JWT(token);
            if (!jwt.isExpired()) {
                Long creationUserId = jwt.getData().getLong("userId");
                int permission = 0;

                if (UserPermissionType.CREATE_BETA_KEYS.hasPermission(permission)) {
                    req.endHandler(ctx -> {
                        Long userId = Long.parseLong(req.getFormAttribute("userId"));
                        long quantity = Long.parseLong(req.getFormAttribute("quantity")); //TODO Return null if error
                        if (userId != null) {
                            SecureRandom random = new SecureRandom();
                            ArrayList<String> keyList = new ArrayList<>();
                            //TODO Check duplicates

                            InsertValuesStep3<UserBetaKeyRecord, Long, String, Long> insertInto = DiluvAPI.getDSLContext().insertInto(USER_BETA_KEY,
                                    USER_BETA_KEY.USER_ID, USER_BETA_KEY.BETA_KEY, USER_BETA_KEY.CREATION_USER_ID);

                            for (long i = 0; i < quantity; i++) {
                                String key = new BigInteger(130, random).toString(32);
                                insertInto.values(userId, key, creationUserId);
                                keyList.add(key);
                            }
                            insertInto.execute();
                            ResponseUtilities.asSuccessResponse(event, keyList);
                        } else {
                            ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "The target userId is needed for this request");
                        }
                    });
                } else {
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.UNAUTHORIZED_REQUEST);
                }
            }
        }
    }
}