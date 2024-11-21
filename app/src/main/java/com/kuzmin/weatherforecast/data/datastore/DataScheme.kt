package com.kuzmin.weatherforecast.data.datastore

import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataScheme {
    val LATITUDE = doublePreferencesKey("latitude")
    val LONGITUDE = doublePreferencesKey("longitude")
    val API_KEY = stringPreferencesKey("api_key")
    val CITY_NAME = stringPreferencesKey("city_name")
}