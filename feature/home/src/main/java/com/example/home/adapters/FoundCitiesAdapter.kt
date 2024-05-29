package com.example.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.ItemCityNameViewHolderBinding

class FoundCitiesAdapter(
    private val onClick: (cityName: String) -> Unit
) : ListAdapter<String, FoundCitiesAdapter.FoundCityViewHolder>(FoundCitiesDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundCityViewHolder {
        val binding = ItemCityNameViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoundCityViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: FoundCityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FoundCityViewHolder(
        private val binding: ItemCityNameViewHolderBinding,
        private val onClick: (cityName: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cityName: String) {
            binding.tvCity.text = cityName
            binding.root.setOnClickListener { onClick(cityName) }
        }
    }

    object FoundCitiesDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}