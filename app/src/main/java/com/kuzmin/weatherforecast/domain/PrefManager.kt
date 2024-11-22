package com.kuzmin.weatherforecast.domain

import com.kuzmin.weatherforecast.domain.model.forecast.CityAndLocation
import com.kuzmin.weatherforecast.domain.model.forecast.Coord

interface PrefManager {
    suspend fun storeLocationData(locationData: Coord)

    suspend fun storeApiKey(apiKey: String)

    suspend fun readLocationData(): Pair<Double, Double>

    suspend fun loadCityAndLocation(): CityAndLocation

    suspend fun readApiKey(): String
}