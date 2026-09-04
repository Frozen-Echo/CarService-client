package com.example.carserviceapp.presentation.service

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carserviceapp.domain.model.ServiceRecord

@Composable
fun ServiceHistoryScreen() {

    val services = listOf(

        ServiceRecord(
            1,
            "Замена масла",
            "12.04.2025",
            120000,
            5500.0
        )
    )

    LazyColumn {

        items(services) { service ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(service.title)

                    Text(service.date)

                    Text(
                        "Пробег: ${service.mileage}"
                    )

                    Text(
                        "Стоимость: ${service.cost} ₽"
                    )
                }
            }
        }
    }
}