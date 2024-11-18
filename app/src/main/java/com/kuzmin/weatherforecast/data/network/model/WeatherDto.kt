package com.kuzmin.weatherforecast.data.network.model

data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
