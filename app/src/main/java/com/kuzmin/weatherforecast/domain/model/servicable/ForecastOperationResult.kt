package com.kuzmin.weatherforecast.domain.model.servicable

import android.content.res.Resources
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.util.exceptions.ForecastNetworkRequestException
import com.kuzmin.weatherforecast.util.exceptions.LocationDataException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class ForecastOperationResult {
    data object Loading : ForecastOperationResult()

    class GetLocationByCityNameFromNetworkSuccess(val location: Coord) : ForecastOperationResult()

    class LocationSavedDatastoreSuccess(val location: Coord) : ForecastOperationResult()

    class GetForecastDataFromDatastoreSuccess(val location: Coord) : ForecastOperationResult()

    data object GetForecastDataFromNetworkSuccess : ForecastOperationResult()

    class Error(val throwable: Throwable): ForecastOperationResult() {

        fun handleError(resources: Resources): String {
            return when(throwable) {
                is ForecastNetworkRequestException -> {
                   throwable.getLocalizedMessage(resources)
                }
                is LocationDataException -> {
                    throwable.getLocalizedMessage(resources)
                }
                is SocketTimeoutException -> {
                    resources.getString(R.string.response_error_timeout)
                }
                is UnknownHostException -> {
                    resources.getString(R.string.response_error_host_unavailable)
                }
                else -> {
                    resources.getString(R.string.response_error_unknown)
                }
            }
        }
    }
}