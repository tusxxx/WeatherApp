package com.example.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities.CitiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    fun removeCity(cityUi: FavoritesScreenState.CityUi) {
        viewModelScope.launch {
            citiesRepository.removeCity(cityUi.cityName)
        }
    }

    private val _state = MutableStateFlow(FavoritesScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            citiesRepository.getCities().collect { cities ->
                _state.value = FavoritesScreenState(
                    cities = cities.map { FavoritesScreenState.CityUi(it.name) }
                )
            }
        }
    }
}