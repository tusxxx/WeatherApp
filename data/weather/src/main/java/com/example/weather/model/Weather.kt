package com.example.weather.model


data class Weather(
    val cityName: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double,
    val windSpeed: Double,
    val iconUrl: String
)
