package com.example.carserviceapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.carserviceapp.presentation.auth.LoginScreen
import com.example.carserviceapp.ui.theme.CarServiceAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()


    @Test
    fun loginScreen_показывает_заголовок_Вход() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
            }
        }
        composeTestRule.onNodeWithText("Вход").assertIsDisplayed()
    }

    @Test
    fun loginScreen_показывает_поле_Email() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
            }
        }
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
    }

    @Test
    fun loginScreen_показывает_кнопку_Войти() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
            }
        }
        composeTestRule.onNodeWithText("Войти").assertIsDisplayed()
    }

    @Test
    fun loginScreen_показывает_кнопку_регистрации() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
            }
        }
        composeTestRule
            .onNodeWithText("Нет аккаунта? Зарегистрироваться")
            .assertIsDisplayed()
    }

    @Test
    fun loginScreen_клик_по_регистрации_вызывает_callback() {
        var clicked = false
        composeTestRule.setContent {
            CarServiceAppTheme {
                LoginScreen(
                    onLoginSuccess = {},
                    onRegisterClick = { clicked = true }
                )
            }
        }
        composeTestRule
            .onNodeWithText("Нет аккаунта? Зарегистрироваться")
            .performClick()
        assert(clicked)
    }
}