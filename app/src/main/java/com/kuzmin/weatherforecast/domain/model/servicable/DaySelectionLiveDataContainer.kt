package com.kuzmin.weatherforecast.domain.model.servicable

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class DaySelectionLiveDataContainer @Inject constructor() {

    private val dayOfMonthLiveData = MutableLiveData<Int>()

    fun getDayOfMonthLiveData(): LiveData<Int> = dayOfMonthLiveData

    fun setDayOfMonth(dayOfMonth: Int) {
        Log.d("DaySelectionLiveDataContainer", "setDayOfMonth: $dayOfMonth")
        dayOfMonthLiveData.value = dayOfMonth
    }
}