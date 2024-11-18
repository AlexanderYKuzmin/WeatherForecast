package com.kuzmin.weatherforecast.domain.model.forecast

data class MainData(

    val temp: Double,

    val feelsLike: Double,

    val tempMin: Double,

    val tempMax: Double,

    val pressure: Int,

    val humidity: Int,

    val tempKf: Double = -1.0,

    val seaLevel: Int = -1,

    val grndLevel: Int = -1
)