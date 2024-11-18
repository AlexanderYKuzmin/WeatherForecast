package com.kuzmin.weatherforecast.data.network.model

data class ForecastJsonContainer(
    val cod: String,

    val message: Int,

    val cnt: Int,

    val list: List<ForecastDataDto>,

    val city: CityDto
)
