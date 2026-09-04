package com.example.carserviceapp.data.remote.dto

data class UserDto(
    val uid: String = "",
    val name: String = "",
    val age: Int = 0,
    val position: String = "",
    val address: String = "",
    val darkTheme: Boolean = false
)