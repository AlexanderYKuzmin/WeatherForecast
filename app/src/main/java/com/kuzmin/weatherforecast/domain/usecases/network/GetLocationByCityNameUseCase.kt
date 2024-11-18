package com.kuzmin.weatherforecast.domain.usecases.network

import com.kuzmin.weatherforecast.domain.Repository
import javax.inject.Inject

class GetLocationByCityNameUseCase @Inject constructor(
    private val repository: Repository
) {
   suspend operator fun invoke(cityName: String) = repository.getLocationByCityName(cityName)
}