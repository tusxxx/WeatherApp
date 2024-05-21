package com.example.weather.model


sealed class Weather {
    data class Success(val data: WeatherData) : Weather()
    data class Error(val error: Throwable) : Weather()
}

data class WeatherData(
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val windSpeed: Double
)
