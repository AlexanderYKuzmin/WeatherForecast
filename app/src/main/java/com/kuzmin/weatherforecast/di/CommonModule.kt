package com.kuzmin.weatherforecast.di

import android.content.Context
import com.kuzmin.weatherforecast.domain.model.servicable.DaySelectionLiveDataContainer
import com.kuzmin.weatherforecast.ui.adapters.ForecastWeekListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun provideForecastWeekListAdapter(
        @ApplicationContext appContext: Context,
        daySelectionLiveDataContainer: DaySelectionLiveDataContainer
    ): ForecastWeekListAdapter {
        return ForecastWeekListAdapter(appContext, daySelectionLiveDataContainer)
    }

    @Singleton
    @Provides
    fun provideDayOfMonthLiveDataContainer() = DaySelectionLiveDataContainer()
}