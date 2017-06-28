package com.diluv.api.utils

import com.diluv.api.models.Tables.AUTHACCESSTOKEN
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTOptions
import io.vertx.ext.web.RoutingContext
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection
import java.util.*
import java.util.regex.Pattern

val BEARER: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)

fun encodeToken(JWT: JWTAuth, data: JsonObject, issuer: String = "jwt", expiresInMinutes: Long = 30, algorithm: String = "none", noTimestamp: Boolean = true): String {
    return JWT.generateToken(data, JWTOptions().apply {
        setExpiresInMinutes(expiresInMinutes)
        setIssuer(issuer)
        setAlgorithm(algorithm)
        setNoTimestamp(noTimestamp)
    })
}

fun decodeToken(token: String): JsonObject {
    val segment = token.split("\\.".toRegex())
    if (segment.size != 3) {
        //TODO Error
    }
    val header = JsonObject(String(Base64.getUrlDecoder().decode(segment[0])))
    val payload = JsonObject(String(Base64.getUrlDecoder().decode(segment[1])))

    return JsonObject()
            .put("header", header)
            .put("payload", payload)
}

fun getExpireFromToken(token: String): Long {

    return getPayload(token).getLong("exp")
}

fun getPayload(token: String): JsonObject {

    return decodeToken(token).getJsonObject("payload")
}

fun isExpired(token: String): Boolean {
    if ((System.currentTimeMillis() / 1000) > getExpireFromToken(token))
        return false

    return true
}

fun isTokenValid(conn: Connection, token: String): Boolean {
    val transaction = DSL.using(conn, SQLDialect.MYSQL)

    val tokenInfo = transaction.select(AUTHACCESSTOKEN.TOKEN)
            .from(AUTHACCESSTOKEN)
            .where(AUTHACCESSTOKEN.TOKEN.eq(token))
            .fetchOne()
    return tokenInfo != null
}

fun RoutingContext.getAuthorizationToken(): String? {

    val auth = this.request().getHeader(HttpHeaders.AUTHORIZATION)
    if (auth != null) {
        val parts = auth.split(" ")
        if (parts.size == 2) {
            val scheme = parts[0]
            val token = parts[1]

            if (BEARER.matcher(scheme).matches()) {
                return token
            }
        }
    }
    return null
}
