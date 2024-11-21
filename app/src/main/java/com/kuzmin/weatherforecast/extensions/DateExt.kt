package com.kuzmin.weatherforecast.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToDateTimeString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return format(dateTimeFormatter)
}

fun LocalDateTime.formatToDateString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return format(dateTimeFormatter)
}