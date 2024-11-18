package com.kuzmin.weatherforecast.extensions

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.formatToDateString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToDateSqlString(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToDateTimeString(): String {
    val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun Date.timeRoundedToDay(): Long {
    return this.time / (1000 * 60 * 60 * 24)
}

fun Date.timeRoundedToHour(): Long {
    return this.time / (1000 * 60 * 60)
}

fun LocalDateTime.formatToDateTimeString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return format(dateTimeFormatter)
}

fun LocalDateTime.formatToDateString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return format(dateTimeFormatter)
}