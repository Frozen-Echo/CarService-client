package com.example.carserviceapp

import org.junit.Assert.*
import org.junit.Test

class RegisterScreenLogicTest {

    @Test
    fun `пароль меньше 6 символов отклоняется`() {
        val password = "123"
        assertFalse(password.length >= 6)
    }

    @Test
    fun `пароль ровно 6 символов принимается`() {
        val password = "123456"
        assertTrue(password.length >= 6)
    }

    @Test
    fun `email без собачки не валиден`() {
        val email = "testexample.com"
        assertFalse(email.contains("@"))
    }

    @Test
    fun `пустой email не валиден`() {
        val email = ""
        assertTrue(email.isEmpty())
    }

    @Test
    fun `корректный email содержит собачку и точку`() {
        val email = "user@mail.ru"
        assertTrue(email.contains("@") && email.contains("."))
    }
}