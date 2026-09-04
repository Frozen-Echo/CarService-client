package com.example.carserviceapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth

val POSITIONS = listOf(
    "Автомеханик",
    "Автоэлектрик",
    "Мастер-приёмщик",
    "Диагност",
    "Шиномонтажник",
    "Кузовщик",
    "Маляр",
    "Администратор",
    "Менеджер по работе с клиентами",
    "Директор филиала"
)

val BRANCHES = listOf(
    "ул. Ленина, 12, Москва",
    "пр. Мира, 45, Москва",
    "ул. Гагарина, 78, Санкт-Петербург",
    "пр. Невский, 100, Санкт-Петербург",
    "ул. Советская, 33, Казань",
    "пр. Победы, 21, Краснодар"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()

    var name     by remember(user) { mutableStateOf(user.name) }
    var age      by remember(user) { mutableStateOf(user.age.takeIf { it > 0 }?.toString() ?: "") }
    var position by remember(user) { mutableStateOf(user.position) }
    var address  by remember(user) { mutableStateOf(user.address) }

    var positionExpanded by remember { mutableStateOf(false) }
    var addressExpanded  by remember { mutableStateOf(false) }

    val email = FirebaseAuth.getInstance().currentUser?.email ?: ""

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    LaunchedEffect(user.darkTheme) {
        onDarkThemeChange(user.darkTheme)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveUser(name, age, position, address, darkTheme)
                    }) {
                        Text("Сохранить")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Почта — только для чтения
            OutlinedTextField(
                value = email,
                onValueChange = {},
                label = { Text("Email") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Возраст") },
                modifier = Modifier.fillMaxWidth()
            )

            // Должность — выпадающий список
            ExposedDropdownMenuBox(
                expanded = positionExpanded,
                onExpandedChange = { positionExpanded = it }
            ) {
                OutlinedTextField(
                    value = position.ifEmpty { "Выберите должность" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Должность") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = positionExpanded,
                    onDismissRequest = { positionExpanded = false }
                ) {
                    POSITIONS.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                position = item
                                positionExpanded = false
                            }
                        )
                    }
                }
            }

            // Адрес филиала — выпадающий список
            ExposedDropdownMenuBox(
                expanded = addressExpanded,
                onExpandedChange = { addressExpanded = it }
            ) {
                OutlinedTextField(
                    value = address.ifEmpty { "Выберите филиал" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Адрес филиала") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = addressExpanded,
                    onDismissRequest = { addressExpanded = false }
                ) {
                    BRANCHES.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                address = item
                                addressExpanded = false
                            }
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Тёмная тема", style = MaterialTheme.typography.bodyLarge)
                Switch(
                    checked = darkTheme,
                    onCheckedChange = onDarkThemeChange
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Выйти из аккаунта", color = Color.White)
            }
        }
    }
}