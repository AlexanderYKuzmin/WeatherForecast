package com.kuzmin.weatherforecast.extensions

import java.time.LocalDateTime
import java.util.Date

fun Long.toDateFromSeconds() = Date(this * 1000)

fun Long.toLocalDateTimeFromSeconds(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this, 0, java.time.ZoneOffset.UTC)
}