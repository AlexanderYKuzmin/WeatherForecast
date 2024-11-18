package com.kuzmin.weatherforecast.domain.usecases.db

import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDataDbUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(): Flow<Forecast> {
        return repository.getCurrentForecast()
    }
}