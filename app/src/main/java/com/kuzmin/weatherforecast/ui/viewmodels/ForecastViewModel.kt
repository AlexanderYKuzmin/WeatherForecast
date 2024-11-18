package com.kuzmin.weatherforecast.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector
import com.kuzmin.weatherforecast.domain.usecases.db.LoadDataDbUseCase
import com.kuzmin.weatherforecast.domain.usecases.graphics.CollectChartDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val loadDataDbUseCase: LoadDataDbUseCase,
    private val collectChartDataUseCase: CollectChartDataUseCase
) : ViewModel() {

    suspend fun loadData(): Flow<Forecast> {
        return loadDataDbUseCase()
    }

    fun populateAndGetChartDataCollector(forecast: Forecast, dayOfMonth: Int): ChartDataCollector {
        return collectChartDataUseCase(forecast, dayOfMonth)
    }

}