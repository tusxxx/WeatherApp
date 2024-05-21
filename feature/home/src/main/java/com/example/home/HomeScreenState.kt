package com.example.home

data class HomeScreenUiState(
    val query: String = "",
    val cities: List<CityUi> = emptyList()
) {

    data class CityUi(
        val name: String,
        val temperature: Double
    )
}
