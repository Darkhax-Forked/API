package com.diluv.api.jwt;

import io.vertx.core.json.JsonObject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Base64;
import java.util.regex.Pattern;

import static com.diluv.api.models.Tables.AUTH_ACCESS_TOKEN;

public class JWT {
    private static final String PATTERN = Pattern.quote(".");
    private String token;
    private Long exp = null;
    private String iss;
    private long iat = System.currentTimeMillis() / 1000;
    private JsonObject data;

    public JWT() {
    }

    public JWT(String token) {
        String[] segment = token.split(PATTERN);
        JsonObject header = new JsonObject(new String(Base64.getDecoder().decode(segment[0].getBytes())));
        JsonObject payload = new JsonObject(new String(Base64.getDecoder().decode(segment[1].getBytes())));

        this.token = token;
        if (payload.containsKey("exp"))
            this.exp = payload.getLong("exp") - payload.getLong("iat");
        if (payload.containsKey("iss"))
            this.iss = payload.getString("iss");
        if (payload.containsKey("data"))
            this.data = payload.getJsonObject("data");
    }


    public JWT setExpiresInSeconds(long seconds) {
        this.exp = seconds;
        return this;
    }

    public JWT setExpiresInMinutes(long minutes) {
        this.exp = minutes * 60;
        return this;
    }

    public JWT setExpiresInHours(long hours) {
        this.setExpiresInMinutes(hours * 60);
        return this;
    }

    public JWT setExpiresInDays(long days) {
        this.setExpiresInHours(days * 24);
        return this;
    }

    public JWT setIssuer(String iss) {
        this.iss = iss;
        return this;
    }

    public JWT setData(JsonObject data) {
        this.data = data;
        return this;
    }

    public JsonObject getData() {
        return this.data;
    }

    public long getExpires() {
        return this.iat + this.exp;
    }

    public boolean isExpired() {
        return (System.currentTimeMillis() / 1000) >= this.getExpires();
    }

    @Override
    public String toString() {
        if (token != null)
            return token;

        JsonObject header = new JsonObject()
                .put("typ", "JWT")
                .put("alg", "none");

        JsonObject payload = new JsonObject()
                .put("iat", this.iat);

        if (this.iss != null)
            payload.put("iss", this.iss);
        if (this.exp != null)
            payload.put("exp", this.getExpires());
        payload.put("data", this.data);

        String headerSegment = base64urlEncode(header.encode());
        String payloadSegment = base64urlEncode(payload.encode());

        return headerSegment + "." + payloadSegment + ".";
    }

    private String base64urlEncode(String str) {
        return base64urlEncode(str.getBytes(StandardCharsets.UTF_8));
    }

    private String base64urlEncode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean validToken(String token) {
        String[] segment = token.split(PATTERN);
        try {
            if (segment.length == 2)
                if (Base64.getDecoder().decode(segment[0]).length > 0 && Base64.getDecoder().decode(segment[1]).length > 0)
                    return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }


    public static boolean isTokenValid(Connection conn, String token) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        String tokenInfo = transaction.select(AUTH_ACCESS_TOKEN.TOKEN)
                .from(AUTH_ACCESS_TOKEN)
                .where(AUTH_ACCESS_TOKEN.TOKEN.eq(token))
                .fetchOne(0, String.class);
        return tokenInfo != null;
    }

    public static boolean isRefreshTokenValid(Connection conn, String token) {
        DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

        String tokenInfo = transaction.select(AUTH_ACCESS_TOKEN.REFRESH_TOKEN)
                .from(AUTH_ACCESS_TOKEN)
                .where(AUTH_ACCESS_TOKEN.REFRESH_TOKEN.eq(token))
                .fetchOne(0, String.class);
        return tokenInfo != null;
    }
}