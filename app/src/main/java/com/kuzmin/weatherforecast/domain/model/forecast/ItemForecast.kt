package com.kuzmin.weatherforecast.domain.model.forecast

import java.time.LocalDateTime

data class ItemForecast(
    val date: LocalDateTime,

    val dateText: String,

    val weather: Weather,

    val mainData: MainData,

    val wind: Wind,

    val sys: Sys,

    val clouds: Clouds
)