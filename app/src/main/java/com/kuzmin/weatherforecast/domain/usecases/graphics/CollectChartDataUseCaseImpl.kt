package com.kuzmin.weatherforecast.domain.usecases.graphics

import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector
import java.time.LocalTime
import javax.inject.Inject

class CollectChartDataUseCaseImpl @Inject constructor(
    private val chartDataCollector: ChartDataCollector
) : CollectChartDataUseCase {

    override fun collectChartData(forecast: Forecast, dayOfMonth: Int): ChartDataCollector {
        return chartDataCollector.apply {
            val xyData = forecast.list
                .filter { it.date.dayOfMonth == dayOfMonth }
                .map {
                    it.date.hour.toFloat() to (Math.round(it.mainData.temp * 100) / 100).toFloat()
                }
            val currentHourOnXAxis = LocalTime.now().hour.toFloat()

            init(xyData)

            createLineDataSetList(xyData, currentHourOnXAxis)
        }
    }
}

interface CollectChartDataUseCase {
    fun collectChartData(forecast: Forecast, dayOfMonth: Int): ChartDataCollector
}

