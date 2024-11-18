package com.kuzmin.weatherforecast.di

import androidx.compose.ui.graphics.Color
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector
import com.kuzmin.weatherforecast.util.valueformater.ProfileChartValueFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GraphicsModule {

    @Singleton
    fun provideChartDataBuilder(
        valueFormatter: ProfileChartValueFormatter,
        xAxis: XAxis,
        yLeftAxis: YAxis,
        yRightAxis: YAxis
    ): ChartDataCollector {
        return ChartDataCollector(valueFormatter, xAxis, yLeftAxis, yRightAxis)
    }

    @Provides
    fun provideProfileChartValueFormatter(): ProfileChartValueFormatter {
        return ProfileChartValueFormatter()
    }

    @Singleton
    @Provides
    fun provideXAxis(): XAxis {
        return XAxis()
    }

    @Named("leftYAxis")
    @Singleton
    @Provides
    fun provideYAxisLeft(): YAxis {
        return YAxis()
    }

    @Named("rightYAxis")
    @Singleton
    @Provides
    fun provideYAxisRight(): YAxis {
        return YAxis()
    }

}