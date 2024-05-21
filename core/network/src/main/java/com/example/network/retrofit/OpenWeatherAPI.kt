package com.example.network.retrofit

import com.example.network.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val apiKey = "10f8b7eb9c6d1064bcc05d902599bf1c"

interface OpenWeatherAPI {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appId: String = apiKey
    ): WeatherResponse
}