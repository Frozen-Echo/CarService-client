package com.example.carserviceapp.presentation.car

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val VAG_BRANDS = mapOf(
    "Volkswagen" to listOf("Golf", "Passat", "Tiguan", "Polo", "Touareg", "Arteon"),
    "Audi"       to listOf("A3", "A4", "A6", "Q3", "Q5", "Q7", "TT"),
    "Skoda"      to listOf("Octavia", "Superb", "Kodiaq", "Karoq", "Fabia", "Scala"),
    "SEAT"       to listOf("Leon", "Ibiza", "Ateca", "Tarraco", "Arona"),
    "Cupra"      to listOf("Formentor", "Leon", "Born", "Ateca"),
    "Porsche"    to listOf("Cayenne", "Macan", "Panamera", "911", "Taycan"),
    "Lamborghini" to listOf("Urus", "Huracan", "Revuelto"),
    "Bentley"    to listOf("Bentayga", "Continental GT", "Flying Spur")
)

val SERVICE_WORKS = listOf(
    "Замена масла и фильтра",
    "Замена воздушного фильтра",
    "Замена салонного фильтра",
    "Замена топливного фильтра",
    "Замена свечей зажигания",
    "Замена тормозных колодок (передние)",
    "Замена тормозных колодок (задние)",
    "Замена тормозных дисков",
    "Замена тормозной жидкости",
    "Замена антифриза",
    "Замена ремня ГРМ",
    "Замена цепи ГРМ",
    "Диагностика ходовой части",
    "Развал-схождение",
    "Замена амортизаторов",
    "Замена шаровых опор",
    "Замена рулевых наконечников",
    "Замена АКБ",
    "Диагностика электрики",
    "Компьютерная диагностика"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(
    onSave: (brand: String, model: String, year: Int, mileage: Int,
             works: List<String>, comment: String) -> Unit,
    onBack: () -> Unit
) {
    var selectedBrand by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf("") }
    var year          by remember { mutableStateOf("") }
    var mileage       by remember { mutableStateOf("") }
    var comment       by remember { mutableStateOf("") }
    var checkedWorks  by remember { mutableStateOf(setOf<String>()) }

    var brandExpanded by remember { mutableStateOf(false) }
    var modelExpanded by remember { mutableStateOf(false) }

    val models = VAG_BRANDS[selectedBrand] ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Новая заявка") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (selectedBrand.isNotEmpty() && selectedModel.isNotEmpty()
                                && year.isNotEmpty() && mileage.isNotEmpty()
                                && checkedWorks.isNotEmpty()
                            ) {
                                onSave(
                                    selectedBrand, selectedModel,
                                    year.toIntOrNull() ?: 0,
                                    mileage.toIntOrNull() ?: 0,
                                    checkedWorks.toList(), comment
                                )
                            }
                        }
                    ) { Text("Создать") }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Бренд
            ExposedDropdownMenuBox(
                expanded = brandExpanded,
                onExpandedChange = { brandExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedBrand.ifEmpty { "Выберите бренд" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Бренд") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = brandExpanded,
                    onDismissRequest = { brandExpanded = false }
                ) {
                    VAG_BRANDS.keys.forEach { brand ->
                        DropdownMenuItem(
                            text = { Text(brand) },
                            onClick = {
                                selectedBrand = brand
                                selectedModel = ""
                                brandExpanded = false
                            }
                        )
                    }
                }
            }

            // Модель
            ExposedDropdownMenuBox(
                expanded = modelExpanded && models.isNotEmpty(),
                onExpandedChange = { if (models.isNotEmpty()) modelExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedModel.ifEmpty { "Выберите модель" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Модель") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                    enabled = selectedBrand.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = modelExpanded,
                    onDismissRequest = { modelExpanded = false }
                ) {
                    models.forEach { model ->
                        DropdownMenuItem(
                            text = { Text(model) },
                            onClick = {
                                selectedModel = model
                                modelExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Год выпуска") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = mileage,
                onValueChange = { mileage = it },
                label = { Text("Пробег (км)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Список работ с чекбоксами
            Text("Необходимые работы:", style = MaterialTheme.typography.titleMedium)

            SERVICE_WORKS.forEach { work ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = work in checkedWorks,
                        onCheckedChange = { checked ->
                            checkedWorks = if (checked)
                                checkedWorks + work
                            else
                                checkedWorks - work
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(work)
                }
            }

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Комментарий (необязательно)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
}