package com.kuzmin.weatherforecast.extensions

import java.time.LocalDateTime

fun Long.toLocalDateTimeFromSeconds(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this, 0, java.time.ZoneOffset.UTC)
}