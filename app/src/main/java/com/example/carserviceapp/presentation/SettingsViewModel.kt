package com.example.carserviceapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carserviceapp.data.remote.dto.UserDto
import com.example.carserviceapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val repository = UserRepository()
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _user = MutableStateFlow(UserDto())
    val user = _user.asStateFlow()

    fun loadUser() {
        viewModelScope.launch {
            try {
                _user.value = repository.getUser(uid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveUser(
        name: String,
        age: String,
        position: String,
        address: String,
        darkTheme: Boolean
    ) {
        viewModelScope.launch {
            try {
                val updated = UserDto(
                    uid       = uid,
                    name      = name,
                    age       = age.toIntOrNull() ?: 0,
                    position  = position,
                    address   = address,
                    darkTheme = darkTheme
                )
                repository.saveUser(updated)
                _user.value = updated
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}