package com.example.carserviceapp.data.remote.dto

data class ServiceDto(

    val id: Int = 0,

    val carId: Int,

    val title: String,

    val serviceDate: String,

    val mileage: Int,

    val price: Int,

    val comment: String
)