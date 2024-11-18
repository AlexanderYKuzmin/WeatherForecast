package com.kuzmin.weatherforecast.domain.model.servicable

import com.kuzmin.weatherforecast.domain.model.forecast.Coord

sealed class ForecastOperationResult {
    class GetLocationSuccess(val location: Coord, city: String) : ForecastOperationResult()

    class LocationSavedDatastoreSuccess(val location: Coord) : ForecastOperationResult()

    data object GetForecastDataFromServerSuccess : ForecastOperationResult()

    class Error(val throwable: Throwable): ForecastOperationResult() {

        fun handleException() {
            //TODO
        }
    }

    data object Loading : ForecastOperationResult()
}