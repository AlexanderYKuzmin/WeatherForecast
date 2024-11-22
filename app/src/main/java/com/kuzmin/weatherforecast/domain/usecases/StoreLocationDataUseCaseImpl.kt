package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class StoreLocationDataUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : StoreLocationDataUseCase {

    override suspend fun storeLocationData(locationData: Coord) {
        prefManager.storeLocationData(locationData)
    }
}

interface StoreLocationDataUseCase {
    suspend fun storeLocationData(locationData: Coord)
}