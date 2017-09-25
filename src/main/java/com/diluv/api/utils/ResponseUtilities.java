package com.diluv.api.utils;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtilities {

    public static HttpServerResponse asJSONResponse(RoutingContext route, int statusCode, Map<String, Object> additionalData, Object data) {
        HttpServerResponse response = route.response();
        response.setStatusCode(statusCode);
        response.putHeader("Content-Type", "application/json; charset=utf-8");
        response.putHeader("Access-Control-Allow-Origin", "*");
        response.putHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        Map<String, Object> out = new HashMap<>();
        out.put("statusCode", statusCode);
        if (data != null)
            out.put("data", data);
        if (additionalData != null)
            out.putAll(additionalData);
        response.end(Json.encodePrettily(out));
        return response;
    }

    public static HttpServerResponse asErrorResponse(RoutingContext route, Errors error, Object additionalData) {

        Map<String, Object> map = new HashMap<>();
        map.put("error", error.errorMessage);

        if (additionalData != null)
            map.put("message", additionalData);
        return ResponseUtilities.asJSONResponse(route, error.statusCode, map, null);
    }

    public static HttpServerResponse asErrorResponse(RoutingContext route, ErrorMessages errorMessages) {
        return ResponseUtilities.asErrorResponse(route, errorMessages.errors, errorMessages.errorMessage);
    }

    public static HttpServerResponse asErrorResponse(RoutingContext route, Errors error, List<ErrorMessages> errorMessagesList) {
        List<String> errorMessages = new ArrayList<>();
        for(ErrorMessages errorMessage:errorMessagesList){
            errorMessages.add(errorMessage.errorMessage);
        }
        return ResponseUtilities.asErrorResponse(route, error, errorMessages);
    }

    public static HttpServerResponse asSuccessResponse(RoutingContext route, Map<String, Object> data) {
        return ResponseUtilities.asJSONResponse(route, 200, null, data);
    }

    public static HttpServerResponse asSuccessResponse(RoutingContext route, List data) {
        return ResponseUtilities.asJSONResponse(route, 200, null, data);
    }
}

