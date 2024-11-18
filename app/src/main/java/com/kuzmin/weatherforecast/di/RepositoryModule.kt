package com.kuzmin.weatherforecast.di

import com.kuzmin.weatherforecast.data.repository.RepositoryImpl
import com.kuzmin.weatherforecast.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}