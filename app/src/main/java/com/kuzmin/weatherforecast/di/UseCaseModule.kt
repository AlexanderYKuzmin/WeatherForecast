package com.kuzmin.weatherforecast.di

import com.kuzmin.weatherforecast.domain.usecases.GetLastKnownLocationDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.GetLastKnownLocationDataUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.LoadDataFromStorageUseCase
import com.kuzmin.weatherforecast.domain.usecases.LoadDataFromStorageUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.StoreLocationDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.StoreLocationDataUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.db.LoadDataDbUseCase
import com.kuzmin.weatherforecast.domain.usecases.db.LoadDataDbUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.graphics.CollectChartDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.graphics.CollectChartDataUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.network.GetForecastNetworkByLocationUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetForecastNetworkByLocationUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.network.GetLocationByCityNameUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetLocationByCityNameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindStoreLocationDataUseCase(impl: StoreLocationDataUseCaseImpl): StoreLocationDataUseCase

    @Binds
    fun bindGetLocationByCityNameUseCase(impl: GetLocationByCityNameUseCaseImpl): GetLocationByCityNameUseCase

    @Binds
    fun bindGetForecastNetworkByLocationUseCase(
        impl: GetForecastNetworkByLocationUseCaseImpl
    ): GetForecastNetworkByLocationUseCase

    @Binds
    fun bindLoadDataFromStorageUseCase(impl: LoadDataFromStorageUseCaseImpl): LoadDataFromStorageUseCase

    @Binds
    fun bindCollectChartDataUseCase(impl: CollectChartDataUseCaseImpl): CollectChartDataUseCase

    @Binds
    fun bindGetLastKnownLocationDataUseCase(impl: GetLastKnownLocationDataUseCaseImpl): GetLastKnownLocationDataUseCase

    @Binds
    fun bindLoadDataDbUseCase(impl: LoadDataDbUseCaseImpl): LoadDataDbUseCase
}