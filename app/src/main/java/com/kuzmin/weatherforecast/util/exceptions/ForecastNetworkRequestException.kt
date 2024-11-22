package com.kuzmin.weatherforecast.util.exceptions

import android.content.res.Resources

class ForecastNetworkRequestException(
    val code: Int,
) : Exception() {

    override var message: String? = null

    fun getLocalizedMessage(res: Resources): String {
        return ResponseErrors.getErrorMessage(code, res)
    }
}