package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import javax.inject.Inject

class GetLastKnowLocationDataUseCase @Inject constructor(
    private val prefManager: PrefManager
) {
    suspend operator fun invoke() = prefManager.readLocationData()
}