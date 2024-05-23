package com.example.weather

import com.example.network.models.WeatherResponse
import com.example.weather.model.Units
import com.example.weather.model.Weather

fun Units.toApiParameter() = when (this) {
    Units.METRIC -> "metric"
    Units.IMPERIAL -> "imperial"
}

fun WeatherResponse.toWeatherData() = Weather(
    cityName = name,
    lat = coord.lat,
    lon = coord.lon,
    temperature = main.temp,
    windSpeed = wind.speed,
    iconUrl = "https://openweathermap.org/img/wn/${weather.first().icon}@2x.png"
)