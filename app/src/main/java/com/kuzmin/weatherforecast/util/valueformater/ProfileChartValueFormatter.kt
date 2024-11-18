package com.kuzmin.weatherforecast.util.valueformater

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import javax.inject.Inject

class ProfileChartValueFormatter @Inject constructor(): ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return if (axis is YAxis) String.format("%.1f", value)
        else String.format("%d", value.toInt())
    }

    override fun getPointLabel(entry: Entry?): String {
        return String.format(" %.0f", entry!!.y)
    }
}