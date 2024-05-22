package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.FirebaseRemote
import com.example.weather.WeatherRepository
import com.example.weather.model.Units
import com.example.weather.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val remoteConfig: FirebaseRemote
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // todo replace with real impl
            val firebaseCities = remoteConfig.fetchCities().first()
            val weathers = firebaseCities.cities.map {
                weatherRepository.getWeather(it.name, Units.METRIC) // todo get untis from prefs
            }.filterIsInstance<Weather.Success>()

            _state.update {
                it.copy(
                    cities = weathers.map {
                        HomeScreenUiState.CityUi(
                            it.data.cityName,
                            HomeScreenUiState.CityUi.Temperature.Celsius(it.data.temperature),
                            iconUrl = it.data.iconUrl
                        )
                    },
                    isLoading = false
                )
            }
        }
    }

    fun updateQuery(newQuery: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(query = newQuery)
            }
        }
    }
}