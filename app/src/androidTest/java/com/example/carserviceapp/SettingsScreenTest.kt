package com.example.carserviceapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.carserviceapp.presentation.SettingsScreen
import com.example.carserviceapp.ui.theme.CarServiceAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsScreen_показывает_заголовок_Настройки() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                SettingsScreen(
                    darkTheme = false,
                    onDarkThemeChange = {},
                    onBack = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Настройки").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_показывает_поле_Имя() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                SettingsScreen(
                    darkTheme = false,
                    onDarkThemeChange = {},
                    onBack = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Имя").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_показывает_переключатель_темы() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                SettingsScreen(
                    darkTheme = false,
                    onDarkThemeChange = {},
                    onBack = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Тёмная тема").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_показывает_кнопку_выхода() {
        composeTestRule.setContent {
            CarServiceAppTheme {
                SettingsScreen(
                    darkTheme = false,
                    onDarkThemeChange = {},
                    onBack = {},
                    onLogout = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Выйти из аккаунта").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_клик_выход_вызывает_callback() {
        var loggedOut = false
        composeTestRule.setContent {
            CarServiceAppTheme {
                SettingsScreen(
                    darkTheme = false,
                    onDarkThemeChange = {},
                    onBack = {},
                    onLogout = { loggedOut = true }
                )
            }
        }
        composeTestRule.onNodeWithText("Выйти из аккаунта").performClick()
        assert(loggedOut)
    }
}