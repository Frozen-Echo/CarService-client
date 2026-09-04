package com.example.carserviceapp

import com.example.carserviceapp.data.remote.dto.CarDto
import org.junit.Assert.*
import org.junit.Test

class CarDtoTest {

    @Test
    fun `CarDto создаётся с дефолтными значениями`() {
        val car = CarDto(brand = "Skoda", model = "Octavia", year = 2022, mileage = 15000)
        assertEquals(0, car.id)
        assertEquals("", car.ownerId)
        assertEquals("", car.works)
        assertEquals("", car.doneWorks)
        assertEquals("", car.comment)
        assertFalse(car.isReady)
    }

    @Test
    fun `copy обновляет только нужные поля`() {
        val car = CarDto(
            id = 1, brand = "Audi", model = "A3",
            year = 2020, mileage = 40000,
            works = "Замена масла", doneWorks = "", isReady = false
        )
        val updated = car.copy(doneWorks = "Замена масла", isReady = true)
        assertEquals("Замена масла", updated.doneWorks)
        assertTrue(updated.isReady)
        assertEquals("Audi", updated.brand) // остальное не изменилось
    }

    @Test
    fun `год производства сохраняется корректно`() {
        val car = CarDto(brand = "VW", model = "Polo", year = 2019, mileage = 75000)
        assertEquals(2019, car.year)
    }

    @Test
    fun `пробег сохраняется корректно`() {
        val car = CarDto(brand = "Porsche", model = "Cayenne", year = 2023, mileage = 5000)
        assertEquals(5000, car.mileage)
    }

    @Test
    fun `строка с одной работой парсится в список из одного элемента`() {
        val car = CarDto(brand = "Seat", model = "Leon", year = 2021,
            mileage = 20000, works = "Замена масла")
        val list = car.works.split(",").filter { it.isNotBlank() }
        assertEquals(1, list.size)
    }

    @Test
    fun `количество выполненных работ считается корректно`() {
        val car = CarDto(
            brand = "Skoda", model = "Kodiaq", year = 2022, mileage = 30000,
            works = "Замена масла,Замена фильтра,Диагностика",
            doneWorks = "Замена масла,Замена фильтра"
        )
        val done = car.doneWorks.split(",").filter { it.isNotBlank() }
        assertEquals(2, done.size)
    }
}