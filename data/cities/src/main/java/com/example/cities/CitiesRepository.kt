package com.example.cities

import com.example.cities.model.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    suspend fun getCities(): Flow<List<City>>

    suspend fun getFavoriteCities(): Flow<List<City>>
}