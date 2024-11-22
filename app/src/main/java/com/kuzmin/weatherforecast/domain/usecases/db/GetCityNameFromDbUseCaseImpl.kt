package com.kuzmin.weatherforecast.domain.usecases.db

import com.kuzmin.weatherforecast.domain.Repository
import javax.inject.Inject

class GetCityNameFromDbUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetCityNameFromDbUseCase {

    override suspend fun getCityName() = repository.getCityName()
}

interface GetCityNameFromDbUseCase {

    suspend fun getCityName(): String
}