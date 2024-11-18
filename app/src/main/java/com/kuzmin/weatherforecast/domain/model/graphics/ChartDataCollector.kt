package com.kuzmin.weatherforecast.domain.model.graphics

import android.graphics.Color
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.kuzmin.weatherforecast.extensions.toGmt
import com.kuzmin.weatherforecast.util.valueformater.ProfileChartValueFormatter
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.abs

class ChartDataCollector @Inject constructor(
    val profileChartValueFormatter: ProfileChartValueFormatter,
    val xAxis: XAxis,
    @Named("leftYAxis") val yLeftAxis: YAxis,
    @Named("rightYAxis") val yRightAxis: YAxis
) {

    private val _lineDataSetList = mutableListOf<ILineDataSet>()
    val lineDataSetList: List<ILineDataSet> get() = _lineDataSetList

    fun init(xyData: List<Pair<Float, Float>>) {
        clear()
        xAxis.apply {
            axisMaximum = setXAxisMaximum(xyData)
        }
        yLeftAxis.apply {
            axisMaximum = setYAxisMaximum(xyData)
            axisMinimum = setYAxisMinimum(xyData)
        }
        yRightAxis.apply {
            axisMaximum = setYAxisMaximum(xyData)
            axisMinimum = setYAxisMinimum(xyData)
        }
    }

    fun createLineDataSetList(xyData: List<Pair<Float, Float>>, currentHourOnXAxis: Float) {
        for (i in xyData.indices) {
            if (i == xyData.size - 1) break
            _lineDataSetList.add(
                createLineDataSet(
                    listOf(xyData[i], xyData[i + 1])
                )
            )
        }
        _lineDataSetList.add(
            createLineDataSetBorder(currentHourOnXAxis)
        )
    }

    private fun createLineDataSet(xyData: List<Pair<Float, Float>>): LineDataSet {
        if (xyData.size < 2) throw RuntimeException("Wrong XY data.")

        val startEntry = Entry(xyData[0].first, xyData[0].second)
        val endEntry = Entry(xyData[1].first, xyData[1].second)

        return LineDataSet(listOf(startEntry, endEntry), null).apply {
            setDrawValues(true)
            setDrawCircles(true)
            color = Color.GREEN
            lineWidth = 2f
            circleRadius = 5f
            circleHoleColor = Color.GREEN
            circleColors = listOf(Color.GREEN)
            valueTextSize = 16f
            valueTextColor = Color.LTGRAY
        }
    }

    private fun createLineDataSetBorder(currentHourOnXAxis: Float): LineDataSet {
        val entriesBorder = listOf(
            Entry(currentHourOnXAxis.toGmt(), yLeftAxis.axisMinimum),
            Entry(currentHourOnXAxis.toGmt(), yLeftAxis.axisMaximum)
        )

        return LineDataSet(entriesBorder, RED_LINE).apply {
            color = Color.RED
            lineWidth = 2f
            setDrawValues(false)
            setDrawCircles(false)
        }
    }

    private fun setYAxisMaximum(xyData: List<Pair<Float, Float>>): Float {
        val maxValue = xyData.maxOf { it.second }
        return maxValue + abs(maxValue) * 0.1f
    }

    private fun setYAxisMinimum(xyData: List<Pair<Float, Float>>): Float {
        val minValue = xyData.minOf { it.second }
        return minValue - abs(minValue) * 0.1f
    }

    private fun setXAxisMaximum(xyData: List<Pair<Float, Float>>): Float {
        val maxValue = xyData.maxOf { it.first }
        return maxValue * 1.25f
    }

    private fun clear() {
        _lineDataSetList.clear()
    }

    companion object {
        const val RED_LINE = "текущее время"

        const val AXIS_TEXT_SIZE = 10f
        const val AXIS_LINE_WIDTH = 2f
        const val X_LABELS_COUNT = 7
    }
}