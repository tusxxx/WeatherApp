package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities.CitiesRepository
import com.example.weather.WeatherRepository
import com.example.weather.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // todo replace with real impl (units from prefs)
            val weatherByCity = citiesRepository.getCities()
                .map { cities ->
                    cities.mapNotNull { city ->
                        weatherRepository.getWeather(city.name, Units.METRIC).firstOrNull()
                    }
                }
                .catch {  }
                .firstOrNull()
                ?: emptyList()

            _state.update {
                it.copy(
                    cities = weatherByCity.map { weather ->
                        HomeScreenUiState.CityUi(
                            name = weather.cityName,
                            temperatureInCelsius = HomeScreenUiState.CityUi.Temperature.Celsius(weather.temperature),
                            iconUrl = weather.iconUrl
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