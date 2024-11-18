package com.kuzmin.weatherforecast.data.repository

import android.util.Log
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.kuzmin.weatherforecast.data.db.WeatherDao
import com.kuzmin.weatherforecast.data.mappers.DbToModelMapper
import com.kuzmin.weatherforecast.data.mappers.DtoToModelMapper
import com.kuzmin.weatherforecast.data.network.ApiService
import com.kuzmin.weatherforecast.data.workers.RefreshWeatherDataWorker
import com.kuzmin.weatherforecast.data.workers.RefreshWeatherDataWorker.Companion.LATITUDE
import com.kuzmin.weatherforecast.data.workers.RefreshWeatherDataWorker.Companion.LONGITUDE
import com.kuzmin.weatherforecast.domain.Repository
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dtoToModelMapper: DtoToModelMapper,
    private val dbToModelMapper: DbToModelMapper,
    private val workManager: WorkManager,
    private val weatherDao: WeatherDao,
) : Repository {

    override suspend fun getForecastByLocation(location: Coord) {
        val data = Data.Builder()
        data.putDouble(LATITUDE, location.lat)
        data.putDouble(LONGITUDE, location.lon)

        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(RefreshWeatherDataWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            RefreshWeatherDataWorker.NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    override suspend fun getLocationByCityName(cityName: String): Coord {
        val response = apiService.getLocationByCityName(cityName)
        Log.d("REQUEST", "Requested location by city name. Response: $response")
        if (response.cod == 200) {
            return dtoToModelMapper.mapCoordDtoToCoord(response.locationData)
        } else Log.d("Location", "getLocationByCityName: ${response.cod}")

        return Coord(0.0, 0.0)
    }

    override suspend fun getCurrentForecast(): Flow<Forecast> {
        return weatherDao.getForecast().mapNotNull {
            it?.let {
                dbToModelMapper.mapForecastDbToForecast(it)
            }
        }.stateIn(CoroutineScope(Dispatchers.IO))
    }
}