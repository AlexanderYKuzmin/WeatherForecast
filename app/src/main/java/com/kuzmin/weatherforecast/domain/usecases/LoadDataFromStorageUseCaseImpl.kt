package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import javax.inject.Inject

class LoadDataFromStorageUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : LoadDataFromStorageUseCase {

    override suspend fun loadCity() = prefManager.loadCity()
}

interface LoadDataFromStorageUseCase {
    suspend fun loadCity(): String
}