package com.kuzmin.weatherforecast.data.network

import com.kuzmin.weatherforecast.data.network.model.ForecastJsonContainer
import com.kuzmin.weatherforecast.data.network.model.SingleDateWeatherJsonContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getLocationByCityName(
        @Query(CITY_NAME) cityName: String,
        @Query(APIID) apiKey: String
    ): SingleDateWeatherJsonContainer

    @GET("forecast")
    suspend fun getWeatherWeekByCoordinates(
        @Query(LATITUDE) latitude: Double,
        @Query(LONGITUDE) longitude: Double,
        @Query(APIID) apiKey: String
    ): ForecastJsonContainer


    companion object {
        private const val LATITUDE = "lat"
        private const val LONGITUDE = "lon"

        private const val CITY_NAME = "q"
        const val APIID = "appid"
        const val UNITS = "units"
        const val LANGUAGE = "lang"
    }
}