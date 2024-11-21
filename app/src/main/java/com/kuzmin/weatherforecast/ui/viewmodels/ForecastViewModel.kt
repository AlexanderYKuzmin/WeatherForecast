package com.kuzmin.weatherforecast.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector
import com.kuzmin.weatherforecast.domain.usecases.db.LoadDataDbUseCase
import com.kuzmin.weatherforecast.domain.usecases.db.LoadDataDbUseCaseImpl
import com.kuzmin.weatherforecast.domain.usecases.graphics.CollectChartDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.graphics.CollectChartDataUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val loadDataDbUseCase: LoadDataDbUseCase,
    private val collectChartDataUseCase: CollectChartDataUseCase
) : ViewModel() {

    private val _dayOfMonthLiveData = MutableLiveData(LocalDateTime.now().dayOfMonth)
    val dayOfMonthLiveData get() = _dayOfMonthLiveData

    suspend fun loadData(): Flow<Forecast> {
        return loadDataDbUseCase.loadForecastFromDb()
    }

    fun populateAndGetChartDataCollector(forecast: Forecast, dayOfMonth: Int): ChartDataCollector {
        return collectChartDataUseCase.collectChartData(forecast, dayOfMonth)
    }

    fun setDayOfMonth(dayOfMonth: Int) {
        _dayOfMonthLiveData.value = dayOfMonth
    }
}