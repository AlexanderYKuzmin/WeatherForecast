package com.kuzmin.weatherforecast.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.databinding.ItemForecastBinding
import com.kuzmin.weatherforecast.domain.model.forecast.ItemForecast
import com.kuzmin.weatherforecast.domain.model.servicable.DaySelectionLiveDataContainer
import com.kuzmin.weatherforecast.extensions.formatToDateString
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ForecastWeekListAdapter @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val daySelectionLiveDataContainer: DaySelectionLiveDataContainer
) : ListAdapter<ItemForecast, ForecastWeekListAdapter.ItemForecastViewHolder>(ForecastDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemForecastViewHolder, position: Int) {
        val itemForecast = getItem(position)
        with(holder.binding) {

            with(itemForecast) {
                tvDate.text = date.formatToDateString()
                tvDayTemperature.text = String.format("%.0f\u00B0C", mainData.temp)
                tvMinMax.text = String.format(
                    "%.0f\u00B0C / %.0f\u00B0C", mainData.tempMin, mainData.tempMax
                )
                tvCloudy.text = weather.description

                root.setOnClickListener {
                    daySelectionLiveDataContainer.setDayOfMonth(date.dayOfMonth)
                }
            }
        }
    }

    inner class ItemForecastViewHolder(
        val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root)


    object ForecastDiffCallback : DiffUtil.ItemCallback<ItemForecast>(){
        override fun areItemsTheSame(oldItem: ItemForecast, newItem: ItemForecast): Boolean {
            return oldItem.date == newItem.date
        }
        override fun areContentsTheSame(oldItem: ItemForecast, newItem: ItemForecast): Boolean {
            return oldItem == newItem
        }
    }
}