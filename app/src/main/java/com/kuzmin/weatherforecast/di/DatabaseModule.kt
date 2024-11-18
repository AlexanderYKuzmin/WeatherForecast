package com.kuzmin.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.kuzmin.weatherforecast.data.db.WeatherDao
import com.kuzmin.weatherforecast.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideWeatherDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java, "weather-db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.weatherDao()
    }
}