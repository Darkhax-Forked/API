package com.diluv.api.utils;

import com.diluv.api.jwt.JWT;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.security.MessageDigest;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.diluv.api.models.Tables.AUTH_ACCESS_TOKEN;

public class AuthorizationUtilities {
    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    public static JWT encodeToken(JsonObject data, String issuer) {
        return new JWT().setIssuer(issuer).setData(data);
    }

    public static String getToken(RoutingContext event) {
        String auth = event.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null) {
            String[] parts = auth.split(" ");
            if (parts.length == 2) {
                String scheme = parts[0];
                String token = parts[1];
                if (BEARER.matcher(scheme).matches()) {
                    return token;
                }
            }
        }
        return null;
    }

    public static String getAuthorizationToken(RoutingContext event) {
        String token = AuthorizationUtilities.getToken(event);

        return token != null && JWT.validToken(token) ? token : null;
    }

    /**
     * Creates an access token for users to
     * @param conn
     * @param userId
     * @param username
     * @return
     */
    public static Map<String, Object> createAccessToken(Connection conn, long userId, String username) {
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

    public static String getMD5Hex(final String inputString) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputString.getBytes());

            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByteData : digest) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
