package com.kuzmin.weatherforecast.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.databinding.FragmentForecastPagerBinding
import com.kuzmin.weatherforecast.ui.adapters.ForecastTabAdapter
import com.kuzmin.weatherforecast.ui.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastPagerFragment : Fragment() {

    private var _binding: FragmentForecastPagerBinding? = null
    val binding: FragmentForecastPagerBinding get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    private val adapter by lazy { ForecastTabAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastPagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpForecast.adapter = adapter

        forecastViewModel.dayOfMonthLiveData.observe(viewLifecycleOwner) {
            adapter.notifyItemChanged(0)
            binding.tabForecast.getTabAt(0)?.select()
        }

        binding.tabForecast.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.background = if (tab.position == 0) {
                    getDrawable(requireContext(), R.drawable.tab_bgd_left)
                } else {
                    getDrawable(requireContext(), R.drawable.tab_bgd_right)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        attachTabLayoutMediator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun attachTabLayoutMediator() {
        TabLayoutMediator(binding.tabForecast, binding.vpForecast) { tab, position ->
            val tabNames = resources.getStringArray(R.array.tab_names)
            tab.text = tabNames[position]
        }.attach()
    }
}