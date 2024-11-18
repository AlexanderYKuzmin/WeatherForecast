package com.kuzmin.weatherforecast.domain.model.forecast

data class City(
    val name: String,

    val country: String,

    val sunrise: Int,

    val sunset: Int
)
