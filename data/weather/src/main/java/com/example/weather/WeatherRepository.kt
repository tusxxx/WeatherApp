package com.example.weather

import com.example.weather.model.Units
import com.example.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(cityName: String, units: Units): Flow<Weather>

    suspend fun getWeather(lat: Double, lon: Double, units: Units): Flow<Weather>
}