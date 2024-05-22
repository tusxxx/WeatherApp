package com.example.weather

import com.example.weather.model.Units
import com.example.weather.model.Weather

interface WeatherRepository {
    suspend fun getWeather(cityName: String, units: Units): Weather

    suspend fun getWeather(lat: Double, lon: Double, units: Units): Weather
}