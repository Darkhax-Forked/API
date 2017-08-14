package com.diluv.api.error

enum class Errors(val statusCode: Int, val errorMessage: String) {
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    I_AM_A_TEAPOT(418, "418 I'm a teapot"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
}