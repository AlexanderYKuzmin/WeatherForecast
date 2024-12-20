package com.kuzmin.weatherforecast.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kuzmin.weatherforecast.databinding.FragmentFiveDayForecastBinding
import com.kuzmin.weatherforecast.ui.adapters.ForecastWeekListAdapter
import com.kuzmin.weatherforecast.ui.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class FiveDaysForecastFragment : Fragment() {

    private var _binding: FragmentFiveDayForecastBinding? = null
    private val binding get() = _binding!!

    private val forecastViewModel: ForecastViewModel by activityViewModels()

    private val forecastWeekListAdapter = ForecastWeekListAdapter {
        forecastViewModel.setDayOfMonth(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiveDayForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvForecastWeek.adapter = forecastWeekListAdapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                forecastViewModel.loadData().collect { forecast ->
                    val list = mutableListOf(forecast.list.first())

                    list.addAll(
                        forecast.list
                            .filter {
                                it.date.dayOfMonth != LocalDateTime.now().dayOfMonth
                                        && it.date.hour == 12
                            }
                    )
                    forecastWeekListAdapter.submitList(list)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FiveDaysForecastFragment()
    }
}