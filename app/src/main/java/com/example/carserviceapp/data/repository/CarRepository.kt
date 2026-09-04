package com.example.carserviceapp.data.repository

import com.example.carserviceapp.data.remote.RetrofitInstance
import com.example.carserviceapp.data.remote.dto.CarDto

class CarRepository {

    suspend fun getCars(ownerId: String): List<CarDto> =
        RetrofitInstance.api.getCars(ownerId)

    suspend fun addCar(car: CarDto) {
        RetrofitInstance.api.addCar(car)
    }

    suspend fun updateCar(car: CarDto) {
        RetrofitInstance.api.updateCar(car)
    }

    suspend fun deleteCar(id: Int) {
        RetrofitInstance.api.deleteCar(id)
    }
}