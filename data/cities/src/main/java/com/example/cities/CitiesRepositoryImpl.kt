package com.example.cities

import com.example.cities.model.City
import com.example.database.AppDatabase
import com.example.database.city.CityEntity
import com.example.firebase.FirebaseRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CitiesRepositoryImpl @Inject constructor(
    private val firebaseRemote: FirebaseRemote,
    private val appDatabase: AppDatabase
) : CitiesRepository {
    override suspend fun getCities(): Flow<List<City>> =
        appDatabase.cityDao().getAllFlow().map {
            it.map {
                City(it.id, it.name)
            }
        }


    override suspend fun getDefaultCities(): Flow<List<City>> = flow {
        runCatching {
            firebaseRemote.fetchCities()
                .first()
                .cities
                .map { City(it.id, it.name) }
        }.onSuccess {
            emit(it)
        }
    }

    override suspend fun writeCity(cityName: String) {
        runCatching {
            appDatabase.cityDao().insert(CityEntity(name = cityName))
        }
    }

    override suspend fun removeCity(cityName: String) {
        runCatching {
            appDatabase.cityDao().delete(cityName)
        }
    }
}