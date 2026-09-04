package com.example.carserviceapp.data.remote

import com.example.carserviceapp.data.remote.dto.CarDto
import com.example.carserviceapp.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.*

interface CarApi {

    @GET("/cars")
    suspend fun getCars(@Query("ownerId") ownerId: String): List<CarDto>

    @POST("/cars")
    suspend fun addCar(@Body car: CarDto): Response<Unit>

    @PUT("/cars")
    suspend fun updateCar(@Body car: CarDto): Response<Unit>

    @DELETE("/cars/{id}")
    suspend fun deleteCar(@Path("id") id: Int): Response<Unit>

    @GET("/users/{uid}")
    suspend fun getUser(@Path("uid") uid: String): UserDto

    @POST("/users")
    suspend fun saveUser(@Body user: UserDto): Response<Unit>
}