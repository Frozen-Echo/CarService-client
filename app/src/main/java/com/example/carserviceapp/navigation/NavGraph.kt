package com.example.carserviceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carserviceapp.presentation.CarViewModel
import com.example.carserviceapp.presentation.CarsScreen
import com.example.carserviceapp.presentation.SettingsScreen
import com.example.carserviceapp.presentation.auth.LoginScreen
import com.example.carserviceapp.presentation.auth.RegisterScreen
import com.example.carserviceapp.presentation.car.AddCarScreen
import com.example.carserviceapp.presentation.car.CarDetailScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val carViewModel: CarViewModel = viewModel()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val start = if (currentUser != null) "cars" else "login"

    NavHost(navController = navController, startDestination = start) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("cars") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("cars") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable("cars") {
            CarsScreen(
                onCarClick = { carId -> navController.navigate("car_detail/$carId") },
                onAddClick = { navController.navigate("add_car") },
                onSettingsClick = { navController.navigate("settings") },
                viewModel = carViewModel
            )
        }

        composable("add_car") {
            AddCarScreen(
                onSave = { brand, model, year, mileage, works, comment ->
                    carViewModel.addCar(brand, model, year, mileage, works, comment)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("car_detail/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")?.toIntOrNull() ?: 0
            val cars by carViewModel.cars.collectAsState()
            val car = cars.firstOrNull { it.id == carId }

            if (car != null) {
                CarDetailScreen(
                    car = car,
                    viewModel = carViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("settings") {
            SettingsScreen(
                darkTheme = darkTheme,
                onDarkThemeChange = onDarkThemeChange,
                onBack = { navController.popBackStack() },
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}