package com.kuzmin.weatherforecast.domain.usecases.network

import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class GetForecastNetworkByLocationUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetForecastNetworkByLocationUseCase {

    override suspend fun getForecastByLocation(location: Coord) = repository.getForecastByLocation(location)
}

interface GetForecastNetworkByLocationUseCase {
    suspend fun getForecastByLocation(location: Coord)
}