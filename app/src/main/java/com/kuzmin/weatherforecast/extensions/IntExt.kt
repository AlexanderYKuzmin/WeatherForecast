package com.kuzmin.weatherforecast.extensions

fun Int.toMmHg(): Int = (this / 33.86).toInt()