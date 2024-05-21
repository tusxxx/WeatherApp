package com.example.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.ItemCityViewHolderBinding

class CitiesAdapter : ListAdapter<HomeScreenUiState.CityUi, CitiesAdapter.CityViewHolder>(CitiesDiffUtil) {

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

    class CityViewHolder(private val binding: ItemCityViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cityUi: HomeScreenUiState.CityUi) {
            binding.tvCity.text = cityUi.name
            binding.tvTemperature.text = cityUi.temperature.toString()
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