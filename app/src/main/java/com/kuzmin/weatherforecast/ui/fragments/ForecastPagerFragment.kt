package com.kuzmin.weatherforecast.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.databinding.FragmentForecastPagerBinding
import com.kuzmin.weatherforecast.domain.model.servicable.DaySelectionLiveDataContainer
import com.kuzmin.weatherforecast.ui.adapters.ForecastTabAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class ForecastPagerFragment : Fragment() {
    @Inject
    @ApplicationContext
    lateinit var appContext: Context

    @Inject
    lateinit var daySelectionLiveDataContainer: DaySelectionLiveDataContainer

    private var _binding: FragmentForecastPagerBinding? = null
    val binding: FragmentForecastPagerBinding get() = _binding!!

    var dayOfMonth = LocalDateTime.now().dayOfMonth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ForecastTabAdapter(this)
        binding.vpForecast.adapter = adapter

        daySelectionLiveDataContainer.getDayOfMonthLiveData().observe(viewLifecycleOwner) {
            Log.d("DaySelectionLiveDataContainer", "Observing day of month: $it")
            dayOfMonth = it
            adapter.notifyItemChanged(0)
            binding.tabForecast.getTabAt(0)?.select()
        }




        binding.tabForecast.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.background = if (tab.position == 0) {
                    AppCompatResources.getDrawable(appContext, R.drawable.tab_bgd_left)
                } else {
                    AppCompatResources.getDrawable(appContext, R.drawable.tab_bgd_right)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        attachTabLayoutMediator()
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tabForecast, binding.vpForecast) { tab, position ->
            val tabNames = resources.getStringArray(R.array.tab_names)
            tab.text = tabNames[position]
        }.attach()
    }
}