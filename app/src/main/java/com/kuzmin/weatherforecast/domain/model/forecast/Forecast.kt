package com.kuzmin.weatherforecast.domain.model.forecast

data class Forecast(

    val city: City,

    val coord: Coord,

    val list: List<ItemForecast>

)
