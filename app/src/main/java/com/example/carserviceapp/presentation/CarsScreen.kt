package com.example.carserviceapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carserviceapp.utils.getCarImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsScreen(
    onCarClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: CarViewModel = viewModel()
) {
    val cars by viewModel.cars.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.loadCars() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Заявки", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cars) { car ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCarClick(car.id) },
                    elevation = CardDefaults.elevatedCardElevation(6.dp)
                ) {
                    Column {

                        // Фото автомобиля
                        Image(
                            painter = painterResource(
                                id = getCarImageRes(context, car.brand, car.model)
                            ),
                            contentDescription = "${car.brand} ${car.model}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = "${car.brand} ${car.model}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Год: ${car.year}")
                            Text("Пробег: ${car.mileage} км")
                            Spacer(modifier = Modifier.height(8.dp))

                            // Статус
                            Text(
                                text = if (car.isReady) "✓ Готов к выдаче"
                                else "⚙ В работе",
                                color = if (car.isReady) Color(0xFF2E7D32)
                                else Color(0xFFC62828),
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { viewModel.deleteCar(car.id) },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Удалить")
                            }
                        }
                    }
                }
            }
        }
    }
}