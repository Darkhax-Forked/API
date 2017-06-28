package com.diluv.api.utils;

import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

fun RoutingContext.asJSONResponse(statusCode: Int, data: Any): HttpServerResponse {
    val response = this.response()
    response.statusCode = statusCode
    response.putHeader("Content-Type", "application/json; charset=utf-8")
    response.putHeader("Access-Control-Allow-Origin", "*")
    response.putHeader("Access-Control-Allow-Methods", "GET, POST")
    response.putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
    response.end(Json.encodePrettily(data))
    return response
}

fun RoutingContext.asErrorResponse(statusCode: Int, data: Any): HttpServerResponse {
    val map = mapOf("errorMessage" to data)
    return this.asJSONResponse(statusCode, map)
}

fun RoutingContext.asSuccessResponse(data: Map<String, Any>): HttpServerResponse {
    return this.asJSONResponse(200, data)
}

fun RoutingContext.asSuccessResponse(data: List<Any>): HttpServerResponse {
    return this.asJSONResponse(200, data)
}