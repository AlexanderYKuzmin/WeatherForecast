package com.kuzmin.weatherforecast.extensions

import kotlin.math.ceil

fun Float.roundToCeil(): Float {
    return ceil(this)
}

fun Float.toGmt(): Float = this - 3