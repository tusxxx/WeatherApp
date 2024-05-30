package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cities.CitiesRepository
import com.example.preferences.AppPreferenceManager
import com.example.preferences.model.TemperatureUnits
import com.example.weather.WeatherRepository
import com.example.weather.model.Units
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
    private val prefs: AppPreferenceManager
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> get() = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {

            citiesRepository.getCities()
                .map { cities ->
                    cities.mapNotNull { city ->
                        weatherRepository.getWeather(city.name, prefs.temperature.toUnits())
                            .firstOrNull()
                    }
                }
                .collectLatest { weatherByCity ->
                    _state.update {
                        it.copy(
                            cities = weatherByCity.map { weather ->
                                HomeScreenUiState.CityUi(
                                    name = weather.cityName,
                                    temperature = prefs
                                        .temperature
                                        .toUnits()
                                        .toTemperatureUI(weather.temperature),
                                    iconUrl = weather.iconUrl
                                )
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun updateQuery(newQuery: String) {
        viewModelScope.launch {
            _state.update { state ->
                val filteredCities = citiesRepository
                    .getDefaultCities()
                    .map { cities ->
                        if (newQuery.isBlank()) {
                            emptyList()
                        } else {
                            cities
                                .filter { it.name.contains(newQuery, true) }
                                .map { it.name }
                        }
                    }
                    .firstOrNull()
                    ?: emptyList()

                state.copy(query = newQuery, filteredCityNames = filteredCities)
            }
        }
    }

    private fun Units.toTemperatureUI(temp: Double) = when (this) {
        Units.METRIC -> HomeScreenUiState.CityUi.Temperature.Celsius(temp)

        Units.IMPERIAL -> HomeScreenUiState.CityUi.Temperature.Fahrenheit(temp)
    }

    private fun TemperatureUnits.toUnits() = when (this) {
        TemperatureUnits.CELSIUS -> Units.METRIC
        TemperatureUnits.FAHRENHEIT -> Units.IMPERIAL
        TemperatureUnits.KELVIN -> Units.METRIC
    }

    fun toggleCity(cityName: String) {
        viewModelScope.launch {
            val existingCityNames = citiesRepository
                .getCities()
                .firstOrNull()
                ?.map { it.name }
                ?: emptyList()
            if (cityName in existingCityNames) {
                citiesRepository.removeCity(cityName)
            } else {
                citiesRepository.writeCity(cityName)
            }
        }
    }
}