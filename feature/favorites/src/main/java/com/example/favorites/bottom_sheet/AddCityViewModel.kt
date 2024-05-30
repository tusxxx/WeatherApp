package com.example.favorites.bottom_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AddCityBottomSheetState> =
        MutableStateFlow(AddCityBottomSheetState())
    val state: StateFlow<AddCityBottomSheetState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            citiesRepository.getDefaultCities().collect { cities ->
                _state.update {
                    val allCities = cities.map {
                        AddCityBottomSheetState.CityUi(
                            cityName = it.name,
                            isAdded = it.name in (citiesRepository.getCities().firstOrNull()
                                ?.map { it.name } ?: emptyList()),
                            id = it.id
                        )
                    }
                    it.copy(
                        filteredCities = allCities,
                        cities = allCities
                    )
                }
            }
        }
    }

    fun markCity(city: AddCityBottomSheetState.CityUi) {
        viewModelScope.launch {
            if (city.isAdded)
                citiesRepository.removeCity(city.cityName)
            else
                citiesRepository.writeCity(cityName = city.cityName)

            _state.update {
                val newCities = it.cities.map {
                    if (it.id == city.id) {
                        it.copy(isAdded = !city.isAdded)
                    } else {
                        it
                    }
                }
                val newFilteredCities = it.filteredCities.map {
                    if (it.id == city.id) {
                        it.copy(isAdded = !city.isAdded)
                    } else {
                        it
                    }
                }
                it.copy(
                    cities = newCities,
                    filteredCities = newFilteredCities
                )
            }
        }
    }

    fun updateQuery(newQuery: String) {
        viewModelScope.launch {
            _state.update {
                val filteredCities = if (newQuery.isBlank()) {
                    it.cities
                } else {
                    it.cities.filter { city ->
                        city.cityName.contains(newQuery, ignoreCase = true)
                    }
                }
                it.copy(query = newQuery, filteredCities = filteredCities)
            }
        }
    }

}
