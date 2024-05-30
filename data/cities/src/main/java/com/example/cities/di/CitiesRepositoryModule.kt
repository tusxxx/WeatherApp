package com.example.cities.di

import com.example.cities.CitiesRepository
import com.example.cities.CitiesRepositoryImpl
import com.example.database.AppDatabase
import com.example.firebase.FirebaseRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CitiesRepositoryModule {
    @Provides
    @Singleton
    fun provideCitiesRepository(
        firebaseRemote: FirebaseRemote,
        appDatabase: AppDatabase
    ): CitiesRepository {
        return CitiesRepositoryImpl(
            firebaseRemote = firebaseRemote,
            appDatabase = appDatabase
        )
    }
}