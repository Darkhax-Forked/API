package com.diluv.api.utils

import com.diluv.api.jwt.JWT
import com.diluv.api.jwt.validToken
import com.diluv.api.models.Tables.AUTHACCESSTOKEN
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection
import java.util.regex.Pattern

val BEARER: Pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE)

fun encodeToken(data: JsonObject, issuer: String = "jwt", expiresInSeconds: Long = 0, expiresInMinutes: Long = 0, expiresInHours: Long = 0, expiresInDays: Long = 0): JWT {
    val jwt = JWT().setIssuer(issuer)
            .setData(data)

    if (expiresInSeconds != 0L)
        jwt.setExpiresInSeconds(expiresInSeconds)
    if (expiresInMinutes != 0L)
        jwt.setExpiresInMinutes(expiresInMinutes)
    if (expiresInHours != 0L)
        jwt.setExpiresInHours(expiresInHours)
    if (expiresInDays != 0L)
        jwt.setExpiresInDays(expiresInDays)
    return jwt
}

fun RoutingContext.getToken(): String? {
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

/**
 * conn: The connection method
 * isToken: True if the token is a normal token, False if token is a refresh token
 */
fun RoutingContext.getAuthorizationToken(conn: Connection, isToken: Boolean): JWT? {

    val token = this.getToken()
    if (token != null && validToken(token)) {
        val jwt = JWT(token)
        if (!jwt.isExpired()) {
            if ((isToken && jwt.isTokenValid(conn)) || !isToken) {
                return jwt
            } else {
                this.asErrorResponse(400, "Token is not valid")
                return null
            }
        } else {
            this.asErrorResponse(400, "Token is expired")
            return null
        }
    } else {
        this.asErrorResponse(400, "Token is not a valid format")
        return null
    }
}