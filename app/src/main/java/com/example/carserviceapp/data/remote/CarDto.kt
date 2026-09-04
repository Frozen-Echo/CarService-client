package com.example.carserviceapp.data.remote.dto

data class CarDto(
    val id: Int = 0,
    val ownerId: String = "",
    val brand: String = "",
    val model: String = "",
    val year: Int = 0,
    val mileage: Int = 0,
    val works: String = "",
    val doneWorks: String = "",
    val comment: String = "",
    val isReady: Boolean = false
)