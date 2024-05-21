package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.WeatherRepository
import com.example.weather.model.Units
import com.example.weather.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUiState.mockState())
    val state: StateFlow<HomeScreenUiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // todo replace with real impl
            val weather = weatherRepository.getWeather(40.71, -74.0, Units.METRIC)
            when (weather) {
                is Weather.Error -> {

                }
                is Weather.Success -> _state.update {
                    it.copy(
                        cities = listOf(
                            HomeScreenUiState.CityUi(
                                weather.data.cityName,
                                HomeScreenUiState.CityUi.Temperature.Celsius(weather.data.temperature),
                                iconUrl = weather.data.iconUrl
                            )
                        )
                    )
                }
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