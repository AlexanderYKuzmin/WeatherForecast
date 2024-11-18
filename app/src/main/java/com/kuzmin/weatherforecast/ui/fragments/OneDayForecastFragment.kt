package com.kuzmin.weatherforecast.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.databinding.FragmentOneDayForecastBinding
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import com.kuzmin.weatherforecast.domain.model.forecast.ItemForecast
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector.Companion.AXIS_LINE_WIDTH
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector.Companion.AXIS_TEXT_SIZE
import com.kuzmin.weatherforecast.domain.model.graphics.ChartDataCollector.Companion.X_LABELS_COUNT
import com.kuzmin.weatherforecast.extensions.formatToDateString
import com.kuzmin.weatherforecast.extensions.toMmHg
import com.kuzmin.weatherforecast.ui.viewmodels.ForecastViewModel
import com.kuzmin.weatherforecast.util.AppConstants.DAY_OF_MONTH
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class OneDayForecastFragment : Fragment() {
    private var dayOfMonth: Int? = null

    private var _binding: FragmentOneDayForecastBinding? = null
    private val binding get() = _binding!!

    private val forecastViewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dayOfMonth = arguments?.getInt(DAY_OF_MONTH)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneDayForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pbLoading.visibility = View.VISIBLE

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                forecastViewModel.loadData().collect { forecast ->
                    val dayItem = forecast.list
                        .filter { it.date.dayOfMonth == dayOfMonth }.first()

                    binding.pbLoading.visibility = View.GONE
                    fillData(dayItem)

                    setChart(forecast)
                }
            }
        }
    }

    private fun fillData(dayItem: ItemForecast) {
        val locale = Locale("ru", "RU")
        with(binding) {
            tvForecastDay.text = dayItem.date.dayOfWeek
                .getDisplayName(TextStyle.FULL, locale)
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
            tvDate.text = dayItem.date.formatToDateString()
            //tvDayTemperature.text= String.format("%.0f\u00B0C", dayItem.mainData.temp)
            tvDayTemperature.text= String.format(getString(R.string.celcius), dayItem.mainData.temp)
            tvFeeling.text= String.format(getString(R.string.celcius), dayItem.mainData.feelsLike)
            tvMinMaxTemp.text =
                String.format(
                    getString(R.string.min_max_temp_string),
                    dayItem.mainData.tempMin,
                    dayItem.mainData.tempMax
                )
            tvWeatherDesc.text = dayItem.weather.description
            tvWind.text = String.format(getString(R.string.wind_speed), dayItem.wind.speed)
            tvPressure.text = String.format(getString(R.string.pressure_value), dayItem.mainData.pressure.toMmHg())
            tvHumidity.text = String.format(getString(R.string.humidity_value), dayItem.mainData.humidity)
        }
    }

    private fun setChart(forecast: Forecast) {
        if (forecast.list.isEmpty()) throw RuntimeException("No data")

        val chartDataCollector =
            forecastViewModel.populateAndGetChartDataCollector(
                forecast,
                dayOfMonth ?: LocalDateTime.now().dayOfMonth
            )

        binding.lineChart.apply {
            setScaleEnabled(true)
            legend.apply {
                isEnabled = false
            }
            xAxis.apply {
                with(chartDataCollector) {
                    axisMinimum = this.xAxis.axisMinimum
                    axisLineWidth = this.xAxis.axisLineWidth
                    labelCount = X_LABELS_COUNT
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = profileChartValueFormatter
                    textSize = AXIS_TEXT_SIZE
                    axisLineWidth = AXIS_LINE_WIDTH
                    textColor = Color.LTGRAY
                    description.isEnabled = false
                }
            }

            axisLeft.apply {
                with(chartDataCollector) {
                    axisMaximum = yLeftAxis.axisMaximum
                    axisMinimum = yLeftAxis.axisMinimum
                    textSize = AXIS_TEXT_SIZE
                    textColor = Color.LTGRAY
                    setDrawZeroLine(false)
                    setDrawAxisLine(true)
                    valueFormatter = profileChartValueFormatter
                }
            }

            axisRight.apply {
                with(chartDataCollector) {
                    isEnabled = true
                    axisMaximum = yRightAxis.axisMaximum
                    axisMinimum = yRightAxis.axisMinimum
                    textSize = AXIS_TEXT_SIZE
                    textColor = Color.LTGRAY
                    setDrawZeroLine(false)
                    setDrawLabels(false)
                }
            }

            data = LineData(chartDataCollector.lineDataSetList).apply {
                setValueFormatter(chartDataCollector.profileChartValueFormatter)
            }
            invalidate()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(day: Int) = OneDayForecastFragment().apply {
            arguments = Bundle().apply {
                putInt(DAY_OF_MONTH, day)
            }
        }
    }
}