package com.kuzmin.weatherforecast.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.weatherforecast.data.datastore.DataScheme.LATITUDE
import com.kuzmin.weatherforecast.data.datastore.DataScheme.LONGITUDE
import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.util.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : PrefManager {
    val dataStore = context.dataStore

    override suspend fun storeLocationData(locationData: Coord, city: String) {
        with(locationData) {
            dataStore.edit { prefs ->
                prefs[LATITUDE] = lat
                prefs[LONGITUDE] = lon
                prefs[DataScheme.CITY_NAME] = city
            }
        }
    }

    override suspend fun storeApiKey(apiKey: String) {
        TODO("Not yet implemented")
    }

    override suspend fun readLocationData(): Pair<Double, Double> {
        with(DataScheme) {
            return dataStore.data.map { prefs ->
                val latitude = prefs[LATITUDE] ?: -91.0
                val longitude = prefs[LONGITUDE] ?: -181.0

                Pair(latitude, longitude)
            }.first()
        }
    }

    override suspend fun loadCity(): String {
        return dataStore.data.map { prefs ->
            prefs[DataScheme.CITY_NAME] ?: AppConstants.APP_NAME
        }.first()
    }

    override suspend fun readApiKey(): String {
        TODO("Not yet implemented")
    }
}