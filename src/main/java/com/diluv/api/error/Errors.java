package com.diluv.api.error;

public enum Errors {
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    I_AM_A_TEAPOT(418, "418 I'm a teapot"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),;

    public final int statusCode;
    public final String errorMessage;

    Errors(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}