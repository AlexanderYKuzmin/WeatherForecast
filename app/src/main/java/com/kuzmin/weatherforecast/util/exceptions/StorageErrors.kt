package com.kuzmin.weatherforecast.util.exceptions

import android.content.res.Resources
import com.kuzmin.weatherforecast.R

object StorageErrors {

    fun getLocationErrorMessage(resources: Resources): String {
        return resources.getString(R.string.storage_error_get_location)
    }
}