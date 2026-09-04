package com.example.carserviceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carserviceapp.data.local.dao.CarDao
import com.example.carserviceapp.data.local.entity.CarEntity

@Database(
    entities = [CarEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao
}