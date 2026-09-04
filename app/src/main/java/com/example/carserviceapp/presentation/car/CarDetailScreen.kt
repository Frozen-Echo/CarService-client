package com.example.carserviceapp.presentation.car

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.carserviceapp.data.remote.dto.CarDto
import com.example.carserviceapp.presentation.CarViewModel
import com.example.carserviceapp.utils.getCarImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(
    car: CarDto,
    viewModel: CarViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val allWorks = car.works.split(",").filter { it.isNotBlank() }
    var doneWorks by remember {
        mutableStateOf(
            car.doneWorks.split(",").filter { it.isNotBlank() }.toSet()
        )
    }

    val isReady = doneWorks.size == allWorks.size && allWorks.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${car.brand} ${car.model}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.updateDoneWorks(car, doneWorks.toList())
                        onBack()
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Фото автомобиля
            Image(
                painter = painterResource(
                    id = getCarImageRes(context, car.brand, car.model)
                ),
                contentDescription = "${car.brand} ${car.model}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Статус
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isReady) Color(0xFF2E7D32)
                        else Color(0xFFC62828)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isReady) "Автомобиль готов к выдаче"
                            else "В работе",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Информация
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            "Информация об автомобиле",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("Бренд: ${car.brand}")
                        Text("Модель: ${car.model}")
                        Text("Год выпуска: ${car.year}")
                        Text("Пробег: ${car.mileage} км")
                        if (car.comment.isNotBlank()) {
                            Text("Комментарий: ${car.comment}")
                        }
                    }
                }

                // Список работ
                Text(
                    "Работы:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                allWorks.forEach { work ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = work in doneWorks,
                            onCheckedChange = { checked ->
                                doneWorks = if (checked) doneWorks + work
                                else doneWorks - work
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = work,
                            color = if (work in doneWorks)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}