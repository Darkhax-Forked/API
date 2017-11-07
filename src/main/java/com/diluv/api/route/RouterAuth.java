package com.diluv.api.route;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.models.tables.records.AuthVerifyTokenRecord;
import com.diluv.api.models.tables.records.UserRecord;
import com.diluv.api.utils.AuthorizationUtilities;
import com.diluv.api.utils.Recaptcha;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.impl.RouterImpl;
import org.apache.commons.validator.routines.EmailValidator;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.security.SecureRandom;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.*;

public class RouterAuth extends RouterImpl {
    private final Connection conn;
    private final Vertx vertx;

    public RouterAuth(Connection conn, Vertx vertx) {
        super(vertx);
        this.conn = conn;
        this.vertx = vertx;

        this.post("/register").handler(this::postRegister);
        this.post("/login").handler(this::postLogin);
        this.post("/mfa").handler(this::postMFA);

        this.post("/verify/:emailToken").handler(this::postVerify);
        this.post("/refreshToken").handler(this::postRefreshToken);
    }

    public void postVerify(RoutingContext event) {
        String emailToken = event.request().getParam("emailToken");

        if (emailToken != null) {
            DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
            AuthVerifyTokenRecord token = transaction.selectFrom(AUTH_VERIFY_TOKEN)
                    .where(AUTH_VERIFY_TOKEN.TOKEN.eq(emailToken))
                    .fetchAny();
            if (token != null) {
                transaction.update(USER)
                        .set(USER.VERIFIED_EMAIL, true)
                        .where(USER.ID.eq(token.getUserId()))
                        .execute();

                //TODO Proper response
                ResponseUtilities.asSuccessResponse(event, (List) null);
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_EMAIL_TOKEN_INVALID);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_EMAIL_TOKEN_NULL);
        }
    }

    public void postLogin(RoutingContext event) {
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);

        req.endHandler(ctx -> {
            String inputUsernameEmail = req.getFormAttribute("usernameEmail");
            String inputPassword = req.getFormAttribute("password");
            List<ErrorMessages> errorMessage = new ArrayList<>();

            if (inputUsernameEmail == null)
                errorMessage.add(ErrorMessages.AUTH_LOGIN_USER_EMAIL_NULL);
            if (inputPassword == null)
                errorMessage.add(ErrorMessages.AUTH_LOGIN_PASSWORD_NULL);

            boolean isUsername = true;
            if (EmailValidator.getInstance(false).isValid(inputUsernameEmail))
                isUsername = false;

            DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
            if (errorMessage.size() == 0) {
                UserRecord user;
                if (isUsername) {
                    user = transaction.selectFrom(USER)
                            .where(USER.USERNAME.eq(inputUsernameEmail))
                            .fetchAny();
                } else {
                    user = transaction.selectFrom(USER)
                            .where(USER.EMAIL.eq(inputUsernameEmail))
                            .fetchAny();
                }

                if (user != null) {
                    if (user.getVerifiedEmail()) {
                        if (OpenBSDBCrypt.checkPassword(user.getPassword(), inputPassword.toCharArray())) {
                            long userId = user.getId();
                            String username = user.getUsername();
                            if (user.getMfaEnabled()) {
                                JsonObject jwtData = new JsonObject();
                                jwtData.put("userId", userId);
                                jwtData.put("username", username);
                                jwtData.put("time", System.nanoTime());

                                JWT jwtToken = new JWT(jwtData, "mfaToken").setExpiresInMinutes(10);
                                String token = jwtToken.toString();
                                //TODO Check unique
                                transaction.insertInto(AUTH_MFA_TOKEN, AUTH_MFA_TOKEN.USER_ID, AUTH_MFA_TOKEN.TOKEN)
                                        .values(userId, token)
                                        .execute();

                                Map<String, Object> tokenData = new HashMap<>();
                                tokenData.put("mfa", true);
                                tokenData.put("token", token);
                                tokenData.put("tokenExpires", jwtToken.getExpires());
                                ResponseUtilities.asSuccessResponse(event, tokenData);
                            } else {
                                ResponseUtilities.asSuccessResponse(event, AuthorizationUtilities.createAccessToken(this.conn, userId, username));
                            }
                        } else {
                            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_PASSWORD_INCORRECT);
                        }
                    } else {
                        ResponseUtilities.asErrorResponse(event, ErrorMessages.USER_NOT_VERIFIED);
                    }
                } else {
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.USER_NOT_FOUND);
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, errorMessage);
            }
        });
    }

    public void postMFA(RoutingContext event) {
        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(conn, token)) {
                JWT jwt = new JWT(token);
                if (!jwt.isExpired()) {
                    DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
                    String mfaToken = transaction.select(AUTH_MFA_TOKEN.TOKEN)
                            .from(AUTH_MFA_TOKEN)
                            .where(AUTH_MFA_TOKEN.TOKEN.eq(token))
                            .fetchOne(0, String.class);

                    if (mfaToken != null) {
                        String username = jwt.getData().getString("username");
                        Long userId = jwt.getData().getLong("userId");

                        transaction.delete(AUTH_MFA_TOKEN)
                                .where(AUTH_MFA_TOKEN.TOKEN.eq(token))
                                .execute();

                        ResponseUtilities.asSuccessResponse(event, AuthorizationUtilities.createAccessToken(this.conn, userId, username));
                    } else {
                        ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_MFA_NULL);
                    }
                } else {
                    //TODO Remove from database
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_MFA_EXPIRED);
                }
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_MFA_INVALID);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_MFA_NULL);
        }
    }

    public void postRefreshToken(RoutingContext event) {
        String refreshToken = AuthorizationUtilities.getAuthorizationToken(event);
        if (refreshToken != null) {
            if (JWT.isRefreshTokenValid(conn, refreshToken)) {
                JWT jwt = new JWT(refreshToken);
                if (!jwt.isExpired()) {
                    DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

                    String tokenInfo = transaction.select(AUTH_ACCESS_TOKEN.TOKEN)
                            .from(AUTH_ACCESS_TOKEN)
                            .where(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshToken))
                            .fetchOne(0, String.class);

                    if (tokenInfo != null) {
                        long userId = jwt.getData().getLong("userId");
                        String username = jwt.getData().getString("username");

                        transaction.delete(AUTH_ACCESS_TOKEN)
                                .where(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshToken))
                                .execute();

                        ResponseUtilities.asSuccessResponse(event, AuthorizationUtilities.createAccessToken(this.conn, userId, username));
                    }
                } else {
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_REFRESH_TOKEN_EXPIRED);
                }
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_REFRESH_TOKEN_INVALID);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_REFRESH_TOKEN_NULL);
        }
    }

    public void postRegister(RoutingContext event) {
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);

        req.endHandler(ctx -> {
            String email = req.getFormAttribute("email");
            String username = req.getFormAttribute("username");
            String password = req.getFormAttribute("password");
            String recaptchaResponse = req.getFormAttribute("g-recaptcha-response");

            List<ErrorMessages> errorMessage = new ArrayList<>();

            if (email == null)
                errorMessage.add(ErrorMessages.AUTH_REGISTER_EMAIL_NULL);
            else if (!EmailValidator.getInstance(false).isValid(email))
                errorMessage.add(ErrorMessages.AUTH_REGISTER_EMAIL_INVALID);
            if (username == null)
                errorMessage.add(ErrorMessages.AUTH_REGISTER_USERNAME_NULL);
            else if (!AuthorizationUtilities.validUsername(username))
                errorMessage.add(ErrorMessages.AUTH_REGISTER_USERNAME_INVALID);

            if (password == null) {
                errorMessage.add(ErrorMessages.AUTH_REGISTER_PASSWORD_NULL);
            } else {
                if (!AuthorizationUtilities.validPassword(password))
                    errorMessage.add(ErrorMessages.AUTH_REGISTER_PASSWORD_INVALID);
            }
            if (recaptchaResponse == null)
                errorMessage.add(ErrorMessages.AUTH_RECAPTCHA_NULL);

            if (errorMessage.size() == 0) {
                DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
                Record3<Long, String, String> user = transaction.select(USER.ID, USER.EMAIL, USER.USERNAME)
                        .from(USER)
                        .where(USER.EMAIL.eq(email).or(USER.USERNAME.eq(username)))
                        .fetchOne();
                if (user != null) {
                    if (user.get(USER.EMAIL).equals(email))
                        ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_REGISTER_EMAIL_TAKEN);
                    else if (user.get(USER.USERNAME).equals(username))
                        ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_REGISTER_USERNAME_TAKEN);
                } else {
                    WebClient client = WebClient.create(event.vertx());

                    Recaptcha.verify(client, System.getenv("recaptchaSecretKey"), recaptchaResponse, ar -> {
                        if (ar.succeeded()) {
                            HttpResponse<Buffer> response = ar.result();
                            JsonObject body = response.bodyAsJsonObject();
                            if (body.getBoolean("success")) {
                                byte[] salt = new byte[16];
                                new SecureRandom().nextBytes(salt);
                                String passwordHash = OpenBSDBCrypt.generate(password.toCharArray(), salt, 10);

                                /* TODO Gravatar is disabled until images are pulled from it, this will help prevent emails from being retrievable
                                   from the image url as they are only md5'ed
                                 */
                                UserRecord userResults = transaction.insertInto(USER, USER.EMAIL, USER.USERNAME, USER.PASSWORD, USER.AVATAR)
                                        .values(email, username, passwordHash, "" /*"https://www.gravatar.com/avatar/" + AuthorizationUtilities.getMD5Hex(email.trim().toLowerCase()) + "?d=retro"*/)
                                        .returning(USER.ID)
                                        .fetchOne();

                                JsonObject jwtData = new JsonObject();
                                jwtData.put("email", email);
                                jwtData.put("time", System.nanoTime());
                                JWT jwtToken = new JWT(jwtData, "token").setExpiresInMinutes(60);

                                transaction.insertInto(AUTH_VERIFY_TOKEN, AUTH_VERIFY_TOKEN.USER_ID, AUTH_VERIFY_TOKEN.TOKEN)
                                        .values(userResults.getId(), jwtToken.toString());

                                //TODO Send email
                                //TODO Proper response
                                ResponseUtilities.asSuccessResponse(event, (List) null);
                            } else {
                                ResponseUtilities.asErrorResponse(event, ErrorMessages.AUTH_RECAPTCHA_INVALID);
                            }
                        } else {
                            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
                        }
                    });
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, errorMessage);
            }
        });
    }
}