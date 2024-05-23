package com.example.cities

import com.example.cities.model.City
import com.example.firebase.FirebaseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CitiesRepositoryImpl @Inject constructor(
    private val firebaseRemote: FirebaseRemote
) : CitiesRepository {
    override suspend fun getCities(): Flow<List<City>> = flow {
        runCatching {
            firebaseRemote.fetchCities()
                .first()
                .cities
                .map {
                    City(it.id, it.name)
                }
        }.onSuccess {
            emit(it)
        }
    }

    override suspend fun getFavoriteCities(): Flow<List<City>> = flow {
        TODO("Not yet implemented")
    }
}