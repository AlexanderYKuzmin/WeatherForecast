package com.kuzmin.weatherforecast.domain

import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getForecastByLocation(location: Coord)

    suspend fun getLocationByCityName(cityName: String): Coord

    suspend fun getCurrentForecast(): Flow<Forecast>

    suspend fun getCityName(): String
}