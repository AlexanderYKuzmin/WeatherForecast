package com.kuzmin.weatherforecast.domain

import com.kuzmin.weatherforecast.domain.model.forecast.City
import com.kuzmin.weatherforecast.domain.model.forecast.Coord

interface PrefManager {
    suspend fun storeLocationData(locationData: Coord, city: String)

    suspend fun storeApiKey(apiKey: String)

    suspend fun readLocationData(): Pair<Double, Double>

    suspend fun loadCity(): String

    suspend fun readApiKey(): String
}