package com.example.favorites.bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.favorites.databinding.ItemCityBinding

class CitiesAdapter(
    private val onAddClick: (city: AddCityBottomSheetState.CityUi) -> Unit
) : ListAdapter<AddCityBottomSheetState.CityUi, CitiesAdapter.CityViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position), onAddClick = onAddClick)
    }


    class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddCityBottomSheetState.CityUi, onAddClick: (city: AddCityBottomSheetState.CityUi) -> Unit) {
            binding.ctvFavoriteCity.setOnClickListener {
                onAddClick(item)
            }
            binding.ctvFavoriteCity.text = item.cityName
            binding.ctvFavoriteCity.isChecked = item.isAdded
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<AddCityBottomSheetState.CityUi>() {
        override fun areItemsTheSame(
            oldItem: AddCityBottomSheetState.CityUi,
            newItem: AddCityBottomSheetState.CityUi
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: AddCityBottomSheetState.CityUi,
            newItem: AddCityBottomSheetState.CityUi
        ): Boolean =
            oldItem == newItem
    }
}