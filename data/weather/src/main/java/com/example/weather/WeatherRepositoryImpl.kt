package com.example.weather

import com.example.network.retrofit.OpenWeatherAPI
import com.example.weather.model.Units
import com.example.weather.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherAPI
) : WeatherRepository {
    override suspend fun getWeather(cityName: String, units: Units): Flow<Weather> = flow {
        runCatching {
            val response = weatherApi.getWeather(cityName, units.toApiParameter())
            response.toWeatherData()
        }.onSuccess {
            emit(it)
        }
    }

    override suspend fun getWeather(lat: Double, lon: Double, units: Units): Flow<Weather> = flow {
        runCatching {
            val response = weatherApi.getWeather(lat, lon, units.toApiParameter())
            response.toWeatherData()
        }.onSuccess {
            emit(it)
        }
    }
}
