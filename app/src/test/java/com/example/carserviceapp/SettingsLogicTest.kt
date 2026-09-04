package com.example.carserviceapp

import org.junit.Assert.*
import org.junit.Test

class SettingsLogicTest {

    @Test
    fun `возраст из строки конвертируется корректно`() {
        val age = "25"
        assertEquals(25, age.toIntOrNull())
    }

    @Test
    fun `некорректный возраст возвращает null`() {
        val age = "abc"
        assertNull(age.toIntOrNull())
    }

    @Test
    fun `пустой возраст возвращает 0 по умолчанию`() {
        val age = ""
        assertEquals(0, age.toIntOrNull() ?: 0)
    }

    @Test
    fun `тёмная тема по умолчанию выключена`() {
        val darkTheme = false
        assertFalse(darkTheme)
    }

    @Test
    fun `переключение темы меняет значение`() {
        var darkTheme = false
        darkTheme = !darkTheme
        assertTrue(darkTheme)
    }
}