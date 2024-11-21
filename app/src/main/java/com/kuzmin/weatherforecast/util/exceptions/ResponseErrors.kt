package com.kuzmin.weatherforecast.util.exceptions

import android.content.res.Resources
import com.kuzmin.weatherforecast.R

object ResponseErrors {

    fun getErrorMessage(code: Int, resources: Resources): String {
        with(resources) {
            return when (code) {
                400 -> getString(R.string.response_error_bad_request)
                401 -> getString(R.string.response_error_unauthorised)
                404 -> getString(R.string.response_error_bad_request)
                500 -> getString(R.string.response_error_internal_server_error)
                503 -> getString(R.string.response_error_service_unavailable)
                else -> getString(R.string.response_error_unknown)
            }
        }
    }
}