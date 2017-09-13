package com.diluv.api.route;

import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.models.tables.records.UserRecord;
import com.diluv.api.utils.AuthorizationUtilities;
import com.diluv.api.utils.Recaptcha;
import com.diluv.api.utils.ResponseUtilities;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
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
import java.util.regex.Pattern;

import static com.diluv.api.models.Tables.*;

public class RouterAuth {
    private final Pattern USERNAME = Pattern.compile("([A-Za-z0-9_]+)");

    private Connection conn;

    public RouterAuth(Connection conn) {
        this.conn = conn;
    }

    public void createRouter(Router router) {
        router.post("/auth/login").handler(this::postLogin);
        router.post("/auth/mfa").handler(this::getMFA);
        router.post("/auth/refreshToken").handler(this::getRefreshToken);

        router.post("/auth/register").handler(this::postRegister);
    }

    public Map<String, Object> createAccessToken(long userId, String username) {
        JsonObject jwtData = new JsonObject();
        jwtData.put("userId", userId);
        jwtData.put("username", username);
        jwtData.put("time", System.nanoTime());


        JWT jwtToken = AuthorizationUtilities.encodeToken(jwtData, "token").setExpiresInMinutes(60);
        String token = jwtToken.toString();

        JWT jwtRefreshToken = AuthorizationUtilities.encodeToken(jwtData, "refreshToken").setExpiresInDays(60);
        String refreshToken = jwtRefreshToken.toString();

        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
        int count = transaction.selectCount()
                .from(AUTH_ACCESS_TOKEN)
                .where(AUTH_ACCESS_TOKEN.TOKEN.eq(token).and(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshToken)))
                .fetchOne(0, int.class);

        if (count > 0) {
            //TODO Generate new tokens and refresh
        } else {
            transaction.insertInto(AUTH_ACCESS_TOKEN, AUTH_ACCESS_TOKEN.USER_ID, AUTH_ACCESS_TOKEN.TOKEN, AUTH_ACCESS_TOKEN.REFRESH_TOKEN)
                    .values(userId, token, refreshToken)
                    .execute();
        }

        Map<String, Object> out = new HashMap<>();
        out.put("token", token);
        out.put("tokenExpires", jwtToken.getExpires());
        out.put("refreshToken", refreshToken);
        out.put("refreshTokenExpires", jwtRefreshToken.getExpires());

        return out;
    }

    public void postLogin(RoutingContext event) {
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);

        req.endHandler(ctx -> {
            String inputUsernameEmail = req.getFormAttribute("usernameEmail");
            String inputPassword = req.getFormAttribute("password");
            List<String> errorMessage = new ArrayList<>();

            if (inputUsernameEmail == null)
                errorMessage.add("Username or email is needed to login");
            if (inputPassword == null)
                errorMessage.add("Password is needed to login");

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
                    if (OpenBSDBCrypt.checkPassword(user.getPassword(), inputPassword.toCharArray())) {
                        long userId = user.getId();
                        String username = user.getUsername();
                        if (user.getMfaEnabled()) {
                            JsonObject jwtData = new JsonObject();
                            jwtData.put("userId", userId);
                            jwtData.put("username", username);
                            jwtData.put("time", System.nanoTime());

                            JWT jwtToken = AuthorizationUtilities.encodeToken(jwtData, "mfaToken").setExpiresInMinutes(10);
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
                            ResponseUtilities.asSuccessResponse(event, createAccessToken(userId, username));
                        }
                    } else {
                        ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "The password is incorrect");
                    }
                } else {
                    ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "The user doesn't exist");
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, errorMessage);
            }
        });
    }

    public void getMFA(RoutingContext event) {
        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(conn, token)) {
                JWT jwt = new JWT(token);
                if (!jwt.isExpired()) {
                    String tokenText = token.toString();
                    DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
                    String mfaToken = transaction.select(AUTH_MFA_TOKEN.TOKEN)
                            .from(AUTH_MFA_TOKEN)
                            .where(AUTH_MFA_TOKEN.TOKEN.eq(tokenText))
                            .fetchOne(0, String.class);

                    if (mfaToken != null) {
                        String username = jwt.getData().getString("username");
                        Long userId = jwt.getData().getLong("userId");

                        transaction.delete(AUTH_MFA_TOKEN)
                                .where(AUTH_MFA_TOKEN.TOKEN.eq(tokenText))
                                .execute();

                        //                        transaction.insertInto(ANALYTICSAUTH_MFA_TOKEN, ANALYTICSAUTH_MFA_TOKEN.USERID, ANALYTICSAUTH_MFA_TOKEN.TOKEN)
                        //                                .values(userId, tokenText)
                        //                                .execute()

                        ResponseUtilities.asSuccessResponse(event, createAccessToken(userId, username));
                    } else
                        ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "MFA Token not found");
                } else {
                    ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Token is expired");
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Token is not valid");
            }
        }
    }

    public void getRefreshToken(RoutingContext event) {
        String refreshToken = AuthorizationUtilities.getAuthorizationToken(event);
        if (refreshToken != null) {
            if (JWT.isRefreshTokenValid(conn, refreshToken)) {
                JWT jwt = new JWT(refreshToken);
                if (!jwt.isExpired()) {
                    DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

                    String refreshTokeText = refreshToken.toString();
                    String tokenInfo = transaction.select(AUTH_ACCESS_TOKEN.TOKEN)
                            .from(AUTH_ACCESS_TOKEN)
                            .where(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshTokeText))
                            .fetchOne(0, String.class);

                    if (tokenInfo != null) {
                        long userId = jwt.getData().getLong("userId");
                        String username = jwt.getData().getString("username");

                        transaction.delete(AUTH_ACCESS_TOKEN)
                                .where(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshTokeText))
                                .execute();

                        ResponseUtilities.asSuccessResponse(event, createAccessToken(userId, username));
                    }
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Refresh Token is not valid");
            }
        }
    }

    public void postRegister(RoutingContext event) {
        System.out.println(event.request().remoteAddress().host());
        HttpServerRequest req = event.request();
        req.setExpectMultipart(true);

        req.endHandler(ctx -> {
            String email = req.getFormAttribute("email");
            String username = req.getFormAttribute("username");
            String password = req.getFormAttribute("password");
            String passwordConfirm = req.getFormAttribute("passwordConfirm");
            String recaptchaResponse = req.getFormAttribute("g-recaptcha-response");

            List<String> errorMessage = new ArrayList<>();

            if (email == null)
                errorMessage.add("Registering requires an email");
            else if (!EmailValidator.getInstance(false).isValid(email))
                errorMessage.add("Invalid email address");
            if (username == null)
                errorMessage.add("Registering requires a username");
            else if (!validUsername(username))
                errorMessage.add("Username is not valid");

            if (password == null) {
                errorMessage.add("Registering requires a password");
            } else {
                if (passwordConfirm == null) {
                    errorMessage.add("Registering requires password confirmation");
                } else {
                    if (!password.equals(passwordConfirm))
                        errorMessage.add("Password and Password confirm must match");
                    else if (!validPassword(password))
                        errorMessage.add("Password is not valid");
                }
            }
            if (recaptchaResponse == null)
                errorMessage.add("Registering requires a recaptcha response");

            if (errorMessage.size() == 0) {
                DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);
                Record3<Long, String, String> user = transaction.select(USER.ID, USER.EMAIL, USER.USERNAME)
                        .from(USER)
                        .where(USER.EMAIL.eq(email).or(USER.USERNAME.eq(username)))
                        .fetchOne();
                if (user != null) {
                    if (user.get(USER.EMAIL).equals(email))
                        ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Email is already used, please use a different email");
                    else if (user.get(USER.USERNAME).equals(username))
                        ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Username is already used, please use a different username");
                } else {
                    WebClient client = WebClient.create(event.vertx());

                    Recaptcha.verify(client, System.getenv("recaptchaSecretKey"), recaptchaResponse, ar -> {
                        if (ar.succeeded()) {
                            // Obtain response
                            HttpResponse<Buffer> response = ar.result();
                            JsonObject body = response.bodyAsJsonObject();
                            if (body.getBoolean("success")) {
                                byte[] salt = new byte[16];
                                new SecureRandom().nextBytes(salt);
                                String passwordHash = OpenBSDBCrypt.generate(password.toCharArray(), salt, 10);
                                UserRecord userResults = transaction.insertInto(USER, USER.EMAIL, USER.USERNAME, USER.PASSWORD, USER.AVATAR)
                                        .values(email, username, passwordHash, "")
                                        .returning(USER.ID)
                                        .fetchOne();

                                ResponseUtilities.asSuccessResponse(event, createAccessToken(userResults.get(USER.ID), username));
                            } else {
                                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Recaptcha is not valid");
                            }
                        } else {
                            //TODO
                            System.out.println("Something went wrong " + ar.cause().getLocalizedMessage());
                        }
                    });
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, errorMessage);
            }
        });
    }

    public boolean validUsername(String username) {
        if (username.length() < 6 || username.length() > 20)
            return false;
        return USERNAME.matcher(username).matches();
    }

    public boolean validPassword(String password) {
        if (password.length() < 6 || password.length() > 50)
            return false;
        return true;
    }
}
