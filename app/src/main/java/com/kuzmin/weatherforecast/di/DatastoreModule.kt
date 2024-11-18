package com.kuzmin.weatherforecast.di

import com.kuzmin.weatherforecast.data.datastore.PrefManagerImpl
import com.kuzmin.weatherforecast.domain.PrefManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {

    @Binds
    fun bindWeatherDatastore(prefManagerImpl: PrefManagerImpl): PrefManager
}