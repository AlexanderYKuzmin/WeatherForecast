package com.kuzmin.weatherforecast.util.exceptions

enum class ReqErrorMessage(val code: Int) {

    // 400
    BAD_REQUEST(400),

    // 401
    UNAUTHORIZED(401),

    // 404
    NOT_FOUND(404),

    // 500
    INTERNAL_SERVER_ERROR(500),

    // 503
    SERVICE_UNAVAILABLE(503);

}