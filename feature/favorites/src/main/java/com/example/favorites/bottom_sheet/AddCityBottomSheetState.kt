package com.example.favorites.bottom_sheet

data class AddCityBottomSheetState(
    val cities: List<CityUi> = emptyList(),
    val filteredCities: List<CityUi> = emptyList(),
    val query: String = ""
) {

    data class CityUi(
        val cityName: String,
        val isAdded: Boolean,
        val id: Int
    )
}
