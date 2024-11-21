package com.kuzmin.weatherforecast.util.exceptions

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.core.content.ContextCompat.getString
import dagger.hilt.android.qualifiers.ApplicationContext

class ForecastNetworkRequestException(
    val code: Int,
) : Exception() {

    override var message: String? = null

    fun getLocalizedMessage(res: Resources): String {
        Log.d("REQUEST", "RESPONSE CODE: $code")
        Log.d("REQUEST", "RESPONSE MESSAGE: $message")

        return ResponseErrors.getErrorMessage(code, res)
    }
}