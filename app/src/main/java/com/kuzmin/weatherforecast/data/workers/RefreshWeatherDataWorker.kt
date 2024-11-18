package com.kuzmin.weatherforecast.data.workers

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.kuzmin.weatherforecast.data.db.WeatherDao
import com.kuzmin.weatherforecast.data.db.WeatherDatabase
import com.kuzmin.weatherforecast.data.mappers.DtoToDbModelMapper
import com.kuzmin.weatherforecast.data.network.ApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshWeatherDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val apiService: ApiService,
    private val weatherDao: WeatherDao,
    private val weatherDatabase: WeatherDatabase,
    private val dtoToDbModelMapper: DtoToDbModelMapper
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }

    override suspend fun doWork(): Result {
        val latitude = inputData.getDouble(LATITUDE, 0.0)
        val longitude = inputData.getDouble(LONGITUDE, 0.0)

        val forecastJsonContainer = apiService.getWeatherWeekByCoordinates(latitude, longitude)

        Log.d("WORKER", forecastJsonContainer.toString())
        weatherDatabase.clearAllTables()

        weatherDao.insertForecastData(
            dtoToDbModelMapper.mapForecastDataDtoToForecastDb(
                forecastJsonContainer
            )
        )

        return Result.success()
    }

    private fun createNotification() : Notification {
        TODO()
    }

    companion object {
        const val NAME = "RefreshWeatherDataWorker"

        const val NOTIFICATION_ID = 1

        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}