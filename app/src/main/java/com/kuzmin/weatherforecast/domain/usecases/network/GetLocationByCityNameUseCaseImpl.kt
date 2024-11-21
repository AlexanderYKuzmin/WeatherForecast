package com.kuzmin.weatherforecast.domain.usecases.network

import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class GetLocationByCityNameUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetLocationByCityNameUseCase {

   override suspend fun getLocationByCityName(cityName: String) = repository.getLocationByCityName(cityName)
}

interface GetLocationByCityNameUseCase {
    suspend fun getLocationByCityName(cityName: String): Coord
}