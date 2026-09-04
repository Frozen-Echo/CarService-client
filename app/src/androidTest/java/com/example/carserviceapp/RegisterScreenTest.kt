package com.example.carserviceapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.carserviceapp.presentation.auth.RegisterScreen
import com.example.carserviceapp.ui.theme.CarServiceAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_показывает_заголовок_Регистрация() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                RegisterScreen(onRegisterSuccess = {}, onBackToLogin = {})
            }
        }
        composeTestRule.onNodeWithText("Регистрация").assertIsDisplayed()
    }

    @Test
    fun registerScreen_показывает_поле_Email() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                RegisterScreen(onRegisterSuccess = {}, onBackToLogin = {})
            }
        }
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    @Test
    fun registerScreen_показывает_кнопку_Зарегистрироваться() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                RegisterScreen(onRegisterSuccess = {}, onBackToLogin = {})
            }
        }
        composeTestRule.onNodeWithText("Зарегистрироваться").assertIsDisplayed()
    }

    @Test
    fun registerScreen_показывает_ссылку_на_вход() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                RegisterScreen(onRegisterSuccess = {}, onBackToLogin = {})
            }
        }
        composeTestRule
            .onNodeWithText("Уже есть аккаунт? Войти")
            .assertIsDisplayed()
    }

    @Test
    fun registerScreen_клик_на_войти_вызывает_callback() {
        var clicked = false
        composeTestRule.setContent {
            CarServiceAppTheme {
                RegisterScreen(
                    onRegisterSuccess = {},
                    onBackToLogin = { clicked = true }
                )
            }
        }
        composeTestRule
            .onNodeWithText("Уже есть аккаунт? Войти")
            .performClick()
        assert(clicked)
    }
}