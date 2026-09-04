package com.example.carserviceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.carserviceapp.data.repository.UserRepository
import com.example.carserviceapp.navigation.NavGraph
import com.example.carserviceapp.ui.theme.CarServiceAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var darkTheme by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    try {
                        val user = UserRepository().getUser(uid)
                        darkTheme = user.darkTheme
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            CarServiceAppTheme(darkTheme = darkTheme, dynamicColor = false) {
                NavGraph(
                    darkTheme = darkTheme,
                    onDarkThemeChange = { darkTheme = it }
                )
            }
        }
    }
}