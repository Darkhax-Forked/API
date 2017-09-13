package com.diluv.api.utils;

import com.diluv.api.jwt.JWT;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.regex.Pattern;

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
}
