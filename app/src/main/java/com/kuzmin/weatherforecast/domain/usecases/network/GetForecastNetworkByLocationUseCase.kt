package com.kuzmin.weatherforecast.domain.usecases.network

import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class GetForecastNetworkByLocationUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(location: Coord) = repository.getForecastByLocation(location)
}