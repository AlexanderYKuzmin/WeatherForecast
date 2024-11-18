package com.kuzmin.weatherforecast.domain.usecases

import com.kuzmin.weatherforecast.domain.PrefManager
import javax.inject.Inject

class LoadDataFromStorageUseCase @Inject constructor(
    private val prefManager: PrefManager
) {

    suspend operator fun invoke() = prefManager.loadCity()
}