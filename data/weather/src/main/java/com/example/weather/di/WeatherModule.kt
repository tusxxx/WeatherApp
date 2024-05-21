package com.example.weather.di

import com.example.network.retrofit.OpenWeatherAPI
import com.example.weather.WeatherRepository
import com.example.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object WeatherModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: OpenWeatherAPI): WeatherRepository =
        WeatherRepositoryImpl(weatherApi)
}