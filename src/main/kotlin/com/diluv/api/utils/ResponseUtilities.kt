package com.diluv.api.utils;

import com.diluv.api.error.Errors
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

fun RoutingContext.asJSONResponse(statusCode: Int, additionData: Map<String, Any>?, data: Any?): HttpServerResponse {
    val response = this.response()
    response.statusCode = statusCode
    response.putHeader("Content-Type", "application/json; charset=utf-8")
    response.putHeader("Access-Control-Allow-Origin", "*")
    response.putHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
    response.putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
    var out = mapOf<String, Any>("statusCode" to statusCode)
    if (data != null)
        out += mapOf("data" to data)
    if (additionData != null)
        out += additionData
    response.end(Json.encodePrettily(out))
    return response
}

fun RoutingContext.asErrorResponse(error: Errors, additionData: Any?): HttpServerResponse {
    var map = mapOf<String, Any>("error" to error.errorMessage)
    if (additionData != null)
        map += mapOf("message" to additionData)
    return this.asJSONResponse(error.statusCode, map, null)
}

fun RoutingContext.asSuccessResponse(data: Map<String, Any>): HttpServerResponse {
    return this.asJSONResponse(200, null, data)
}

fun RoutingContext.asSuccessResponse(data: List<Any>): HttpServerResponse {
    return this.asJSONResponse(200, null, data)
}