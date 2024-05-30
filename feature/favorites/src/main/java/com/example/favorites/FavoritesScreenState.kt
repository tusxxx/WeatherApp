package com.example.favorites

data class FavoritesScreenState(
    val cities: List<CityUi> = emptyList()
) {
    data class CityUi(
        val cityName: String
    )
}
