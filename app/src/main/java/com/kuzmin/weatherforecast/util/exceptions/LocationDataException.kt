package com.kuzmin.weatherforecast.util.exceptions

import android.content.res.Resources

class LocationDataException : Exception() {

    fun getLocalizedMessage(resources: Resources): String {
        return StorageErrors.getLocationErrorMessage(resources)
    }
}