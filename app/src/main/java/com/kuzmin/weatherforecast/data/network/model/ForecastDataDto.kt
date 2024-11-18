package com.kuzmin.weatherforecast.data.network.model

import com.google.gson.annotations.SerializedName

data class ForecastDataDto(
    val dt: Long,

    val main: MainDto,

    val weather: List<WeatherDto>,

    val clouds: CloudsDto,

    val wind: WindDto,

    val visibility: Int,

    val pop: Double,

    val sys: SysDto,

    @SerializedName("dt_txt")
    val dtTxt: String
)
