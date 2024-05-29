package com.example.database.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM CityEntity")
    suspend fun getAll(): List<CityEntity>

    @Query("SELECT * FROM CityEntity")
    fun getAllFlow(): Flow<List<CityEntity>>

    @Insert
    suspend fun insert(city: CityEntity)

    @Query("DELETE FROM CityEntity WHERE name = :name")
    suspend fun delete(name: String)
}