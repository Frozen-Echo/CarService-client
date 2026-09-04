package com.example.carserviceapp.data.local.dao

import androidx.room.*
import com.example.carserviceapp.data.local.entity.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert
    suspend fun insertCar(
        car: CarEntity
    )

    @Delete
    suspend fun deleteCar(
        car: CarEntity
    )

    @Query("SELECT * FROM cars")
    fun getCars(): Flow<List<CarEntity>>
}