package com.kuzmin.weatherforecast.domain.usecases.db

import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDataDbUseCaseImpl @Inject constructor(
    private val repository: Repository
) : LoadDataDbUseCase {

    override suspend fun loadForecastFromDb(): Flow<Forecast> {
        return repository.getCurrentForecast()
    }
}

interface LoadDataDbUseCase {
    suspend fun loadForecastFromDb(): Flow<Forecast>
}