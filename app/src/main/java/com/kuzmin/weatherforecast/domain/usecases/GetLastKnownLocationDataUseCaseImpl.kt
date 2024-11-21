package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import javax.inject.Inject

class GetLastKnownLocationDataUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : GetLastKnownLocationDataUseCase{

    override suspend fun getLocationDataFromStorage() = prefManager.readLocationData()
}

interface GetLastKnownLocationDataUseCase {
    suspend fun getLocationDataFromStorage(): Pair<Double, Double>
}