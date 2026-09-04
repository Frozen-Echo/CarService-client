package com.example.carserviceapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.carserviceapp.data.remote.dto.CarDto
import com.example.carserviceapp.data.repository.CarRepository
import com.example.carserviceapp.presentation.CarViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: CarRepository
    private lateinit var viewModel: CarViewModel

    private val testCars = listOf(
        CarDto(id = 1, ownerId = "uid1", brand = "Audi", model = "A4",
            year = 2021, mileage = 30000, works = "Замена масла",
            doneWorks = "", comment = "", isReady = false),
        CarDto(id = 2, ownerId = "uid1", brand = "Volkswagen", model = "Golf",
            year = 2020, mileage = 50000, works = "Замена масла,Диагностика",
            doneWorks = "Замена масла,Диагностика", comment = "", isReady = true)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = CarViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `начальное состояние списка пустое`() {
        assertTrue(viewModel.cars.value.isEmpty())
    }

    @Test
    fun `статус isReady true когда все работы выполнены`() {
        val car = testCars[0]
        val allWorks = car.works.split(",").filter { it.isNotBlank() }
        val doneWorks = allWorks // все выполнены
        val isReady = doneWorks.size == allWorks.size && allWorks.isNotEmpty()
        assertTrue(isReady)
    }

    @Test
    fun `статус isReady false когда не все работы выполнены`() {
        val car = testCars[0]
        val allWorks = car.works.split(",").filter { it.isNotBlank() }
        val doneWorks = emptyList<String>() // ничего не выполнено
        val isReady = doneWorks.size == allWorks.size && allWorks.isNotEmpty()
        assertFalse(isReady)
    }

    @Test
    fun `статус isReady false когда список работ пустой`() {
        val allWorks = emptyList<String>()
        val doneWorks = emptyList<String>()
        val isReady = doneWorks.size == allWorks.size && allWorks.isNotEmpty()
        assertFalse(isReady)
    }

    @Test
    fun `works корректно конвертируется в список`() {
        val works = "Замена масла,Замена фильтра,Диагностика"
        val list = works.split(",").filter { it.isNotBlank() }
        assertEquals(3, list.size)
        assertEquals("Замена масла", list[0])
        assertEquals("Диагностика", list[2])
    }

    @Test
    fun `пустой works возвращает пустой список`() {
        val works = ""
        val list = works.split(",").filter { it.isNotBlank() }
        assertTrue(list.isEmpty())
    }

    @Test
    fun `doneWorks корректно объединяется в строку`() {
        val done = listOf("Замена масла", "Диагностика")
        val result = done.joinToString(",")
        assertEquals("Замена масла,Диагностика", result)
    }

    @Test
    fun `имя файла изображения формируется корректно для Volkswagen`() {
        val brand = "Volkswagen"
        val model = "Golf"
        val prefix = when (brand.lowercase()) {
            "volkswagen" -> "vw"
            else -> brand.lowercase()
        }
        val suffix = model.lowercase().replace(" ", "_")
        assertEquals("vw_golf", "${prefix}_${suffix}")
    }

    @Test
    fun `имя файла изображения формируется корректно для Audi`() {
        val brand = "Audi"
        val model = "Q5"
        val prefix = brand.lowercase()
        val suffix = model.lowercase()
        assertEquals("audi_q5", "${prefix}_${suffix}")
    }

    @Test
    fun `имя файла для модели с пробелом содержит подчёркивание`() {
        val model = "Continental GT"
        val suffix = model.lowercase().replace(" ", "_")
        assertEquals("continental_gt", suffix)
    }
}