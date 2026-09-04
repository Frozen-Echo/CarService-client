package com.example.carserviceapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carserviceapp.data.remote.dto.CarDto
import com.example.carserviceapp.data.repository.CarRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarViewModel : ViewModel() {

    private val repository = CarRepository()
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _cars = MutableStateFlow<List<CarDto>>(emptyList())
    val cars = _cars.asStateFlow()

    fun loadCars() {
        viewModelScope.launch {
            try {
                _cars.value = repository.getCars(uid)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addCar(
        brand: String, model: String,
        year: Int, mileage: Int,
        works: List<String>, comment: String
    ) {
        viewModelScope.launch {
            try {
                repository.addCar(
                    CarDto(
                        ownerId  = uid,
                        brand    = brand,
                        model    = model,
                        year     = year,
                        mileage  = mileage,
                        works    = works.joinToString(","),
                        comment  = comment,
                        isReady  = false
                    )
                )
                loadCars()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateDoneWorks(car: CarDto, doneWorks: List<String>) {
        viewModelScope.launch {
            try {
                val allWorks = car.works.split(",").filter { it.isNotBlank() }
                val ready = doneWorks.size == allWorks.size && allWorks.isNotEmpty()
                val updated = car.copy(
                    doneWorks = doneWorks.joinToString(","),
                    isReady = ready
                )
                repository.updateCar(updated)
                loadCars()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteCar(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteCar(id)
                loadCars()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}