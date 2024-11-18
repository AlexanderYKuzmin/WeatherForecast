package com.kuzmin.weatherforecast.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuzmin.weatherforecast.ui.fragments.FiveDaysForecastFragment
import com.kuzmin.weatherforecast.ui.fragments.ForecastPagerFragment
import com.kuzmin.weatherforecast.ui.fragments.OneDayForecastFragment

class ForecastTabAdapter(
    private val forecastPagerFragment: ForecastPagerFragment
) : FragmentStateAdapter(forecastPagerFragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { OneDayForecastFragment.newInstance(forecastPagerFragment.dayOfMonth) }
            1 -> { FiveDaysForecastFragment.newInstance() }
            else -> throw RuntimeException("Wrong position number")
        }
    }


}