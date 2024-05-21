package com.example.weather

import com.example.network.models.WeatherResponse
import com.example.weather.model.Units
import com.example.weather.model.WeatherData

fun Units.toApiParameter() = when (this) {
    Units.METRIC -> "metric"
    Units.IMPERIAL -> "imperial"
}

fun WeatherResponse.toWeatherData() = WeatherData(
    cityName = name,
    lat = coord.lat,
    lon = coord.lon,
    temperature = main.temp,
    windSpeed = wind.speed
)