package com.kuzmin.weatherforecast.data.network.model

import com.google.gson.annotations.SerializedName

data class SingleDateWeatherJsonContainer(
    @SerializedName("name")
    val cityName: String,

    @SerializedName("coord")
    val locationData: CoordDto,

    val cod: Int
)
