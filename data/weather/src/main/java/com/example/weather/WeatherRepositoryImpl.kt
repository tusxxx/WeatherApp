package com.example.weather

import com.example.network.retrofit.OpenWeatherAPI
import com.example.weather.model.Units
import com.example.weather.model.Weather
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherAPI
) : WeatherRepository {
    override suspend fun getWeather(cityName: String, units: Units): Weather = runCatching {
        val response = weatherApi.getWeather(cityName, units.toApiParameter())
        val weatherData = response.toWeatherData()
        Weather.Success(weatherData)
    }.getOrElse {
        Weather.Error(it)
    }

    override suspend fun getWeather(lat: Double, lon: Double, units: Units): Weather = runCatching {
        val response = weatherApi.getWeather(lat, lon, units.toApiParameter())
        val weatherData = response.toWeatherData()
        Weather.Success(weatherData)
    }.getOrElse {
        Weather.Error(it)
    }
}
