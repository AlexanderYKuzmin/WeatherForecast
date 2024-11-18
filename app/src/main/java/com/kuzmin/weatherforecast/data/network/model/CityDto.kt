package com.kuzmin.weatherforecast.data.network.model

data class CityDto(

    val id: Int,

    val name: String,

    val coord: CoordDto,

    val country: String,

    val population: Int,

    val timezone: Int,

    val sunrise: Int,

    val sunset: Int
)