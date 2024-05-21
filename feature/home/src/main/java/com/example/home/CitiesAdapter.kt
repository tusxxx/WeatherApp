package com.example.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.home.databinding.ItemCityViewHolderBinding

class CitiesAdapter :
    ListAdapter<HomeScreenUiState.CityUi, CitiesAdapter.CityViewHolder>(CitiesDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CityViewHolder(private val binding: ItemCityViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cityUi: HomeScreenUiState.CityUi) {
            binding.tvCity.text = cityUi.name

            binding.tvTemperature.text = when (cityUi.temperatureInCelsius) {
                is HomeScreenUiState.CityUi.Temperature.Celsius -> binding.root.context.getString(R.string.celsius_temperature)
                is HomeScreenUiState.CityUi.Temperature.Fahrenheit -> binding.root.context.getString(
                    R.string.fahrenheit_temperature
                )
            }.format(cityUi.temperatureInCelsius.value)

            Glide.with(binding.root)
                .load(cityUi.iconUrl)
                .into(binding.ivIcon)
        }
    }

    private object CitiesDiffUtil : DiffUtil.ItemCallback<HomeScreenUiState.CityUi>() {
        override fun areItemsTheSame(
            oldItem: HomeScreenUiState.CityUi,
            newItem: HomeScreenUiState.CityUi
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HomeScreenUiState.CityUi,
            newItem: HomeScreenUiState.CityUi
        ): Boolean {
            return oldItem == newItem
        }

    }
}