package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class StoreLocationDataUseCase @Inject constructor(
    private val prefManager: PrefManager
) {

    suspend operator fun invoke(locationData: Coord, city: String) {
        prefManager.storeLocationData(locationData, city)
    }
}