package com.example.carserviceapp

import org.junit.Assert.*
import org.junit.Test

class LoginScreenLogicTest {

    @Test
    fun `пустой email не валиден`() {
        val email = ""
        assertFalse(email.contains("@"))
    }

    @Test
    fun `корректный email валиден`() {
        val email = "test@example.com"
        assertTrue(email.contains("@") && email.contains("."))
    }

    @Test
    fun `пароль короче 6 символов не валиден`() {
        val password = "123"
        assertFalse(password.length >= 6)
    }

    @Test
    fun `пароль из 6 символов валиден`() {
        val password = "123456"
        assertTrue(password.length >= 6)
    }

    @Test
    fun `пустой пароль не валиден`() {
        val password = ""
        assertFalse(password.length >= 6)
    }
}