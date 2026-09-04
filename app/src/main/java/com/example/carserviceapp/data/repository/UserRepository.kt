package com.example.carserviceapp.data.repository

import com.example.carserviceapp.data.remote.RetrofitInstance
import com.example.carserviceapp.data.remote.dto.UserDto

class UserRepository {

    suspend fun getUser(uid: String): UserDto =
        RetrofitInstance.api.getUser(uid)

    suspend fun saveUser(user: UserDto) {
        RetrofitInstance.api.saveUser(user)
    }
}