package com.example.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.favorites.databinding.ItemFavoriteCityBinding

class AddedCitiesAdapter(
    private val onDeleteClick: (FavoritesScreenState.CityUi) -> Unit
) : ListAdapter<FavoritesScreenState.CityUi, AddedCitiesAdapter.CityViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemFavoriteCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position), onDeleteClick = onDeleteClick)
    }

    class CityViewHolder(private val binding: ItemFavoriteCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: FavoritesScreenState.CityUi, onDeleteClick: (FavoritesScreenState.CityUi) -> Unit) {
            binding.ctvFavoriteCity.text = city.cityName
            binding.ibDelete.setOnClickListener { onDeleteClick(city) }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<FavoritesScreenState.CityUi>() {
        override fun areItemsTheSame(
            oldItem: FavoritesScreenState.CityUi,
            newItem: FavoritesScreenState.CityUi
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: FavoritesScreenState.CityUi,
            newItem: FavoritesScreenState.CityUi
        ): Boolean = oldItem == newItem
    }
}
