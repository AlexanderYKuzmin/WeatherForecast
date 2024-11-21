package com.kuzmin.weatherforecast.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.weatherforecast.data.datastore.DataScheme.LATITUDE
import com.kuzmin.weatherforecast.data.datastore.DataScheme.LONGITUDE
import com.kuzmin.weatherforecast.domain.CryptoManager
import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.util.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefManagerImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val cryptoManager: CryptoManager
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
        val encrypted = String(cryptoManager.encrypt(apiKey.toByteArray()))
        dataStore.edit { prefs ->
            prefs[DataScheme.API_KEY] = encrypted
        }
    }

    override suspend fun readLocationData(): Pair<Double, Double> {
        with(DataScheme) {
            return dataStore.data.map { prefs ->
                val latitude = prefs[LATITUDE] ?: 0.0
                val longitude = prefs[LONGITUDE] ?: 0.0

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
        return dataStore.data.map { prefs ->
            val encrypted = prefs[DataScheme.API_KEY] ?: ""

            if (encrypted.isEmpty()) {
                AppConstants.DEFAULT_API_KEY
            } else {
                String(cryptoManager.decrypt(encrypted.toByteArray()))
            }
        }.first()
    }
}