package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.forecast.CityAndLocation
import javax.inject.Inject

class LoadDataFromStorageUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : LoadDataFromStorageUseCase {

    override suspend fun loadCityAndLocation() = prefManager.loadCityAndLocation()
}

interface LoadDataFromStorageUseCase {
    suspend fun loadCityAndLocation(): CityAndLocation
}