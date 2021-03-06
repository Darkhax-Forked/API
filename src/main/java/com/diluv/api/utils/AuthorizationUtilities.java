package com.diluv.api.utils;

import com.diluv.api.DiluvAPI;
import com.diluv.api.jwt.JWT;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.diluv.api.models.Tables.AUTH_ACCESS_TOKEN;

public class AuthorizationUtilities {
    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    private static final Pattern USERNAME = Pattern.compile("([A-Za-z0-9_]+)");


    /**
     * Check if the authorization header is present in the request.
     *
     * @param rc The request made to the server
     * @return If an authorization header exists it will return true, else false
     */
    public static boolean isAuthorizationHeaderPresent(RoutingContext rc) {
        return rc.request().getHeader(HttpHeaders.AUTHORIZATION) != null;
    }

    /**
     * Returns a token from the authorization header of the request, if the token is invalid or not present, it will return null
     *
     * @param rc The request that is made to the api with the header
     * @return The token that is in the authorization header or null if invalid or not present
     */
    public static String getAuthorizationToken(RoutingContext rc) {
        if (!isAuthorizationHeaderPresent(rc))
            return null;

        String auth = rc.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null) {
            String[] parts = auth.split(" ");
            if (parts.length == 2) {
                String scheme = parts[0];
                String token = parts[1];
                if (BEARER.matcher(scheme).matches()) {
                    return token != null && JWT.validToken(token) ? token : null;
                }
            }
        }

        return null;
    }

    /**
     * Creates an access token for the user using the userId and username, it is encrypted to prevent
     * snooping and the forging of the token. Will create a map of token and refresh token and their expire datetime.
     *
     * @param userId   The id of the user for the token to created for.
     * @param username The username of the token for the token to be created for.
     * @return The token, expire datatime of the token, refreshToken and the expire datetime of the refresh token.
     */
    //TODO This needs to be encrypted as a precaution to help restrict snooping and the forging of tokens
    public static Map<String, Object> createAccessToken(long userId, String username) {
        JsonObject jwtData = new JsonObject();
        jwtData.put("userId", userId);
        jwtData.put("username", username);
        jwtData.put("time", System.nanoTime());


        JWT jwtToken = new JWT(jwtData, "token").setExpiresInMinutes(60);
        String token = jwtToken.toString();

        JWT jwtRefreshToken = new JWT(jwtData, "refreshToken").setExpiresInDays(60);
        String refreshToken = jwtRefreshToken.toString();

        int count = DiluvAPI.getDSLContext().selectCount()
                .from(AUTH_ACCESS_TOKEN)
                .where(AUTH_ACCESS_TOKEN.TOKEN.eq(token).and(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(refreshToken)))
                .fetchOne(0, int.class);

        if (count > 0) {
            return createAccessToken(userId, username);
        } else {
            DiluvAPI.getDSLContext().insertInto(AUTH_ACCESS_TOKEN, AUTH_ACCESS_TOKEN.USER_ID, AUTH_ACCESS_TOKEN.TOKEN, AUTH_ACCESS_TOKEN.REFRESH_TOKEN)
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

    public static boolean validUsername(String username) {
        if (username.length() < 3 || username.length() > 20)
            return false;
        return USERNAME.matcher(username).matches();
    }

    public static boolean validPassword(String password) {
        if (password.length() < 6 || password.length() > 50)
            return false;
        return true;
    }
}
