package com.example.healthplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Screen_3(
    navController: NavController,
    resultado: Float
) {
    val recomendaciones = when {
        resultado < 18.5f -> listOf(
            "Aumentar proteínas", "Comer alimentos integrales", "Planificar comidas",
            "Entrenamiento de fuerza", "Dormir 7-8 horas", "Gestionar el estrés",
            "Registrar progreso", "Mantener consistencia", "Cocinar en casa", "Beber suficiente agua"
        )
        resultado < 25.0f -> listOf(
            "Mantener consistencia", "Caminar 10k pasos", "Comer alimentos integrales",
            "Dormir 7-8 horas", "Beber suficiente agua", "Entrenamiento de fuerza",
            "Gestionar el estrés", "Planificar comidas", "Cocinar en casa", "Aumentar proteínas"
        )
        resultado < 30.0f -> listOf(
            "Déficit calórico", "Caminar 10k pasos", "Reducir azúcares",
            "Aumentar proteínas", "Beber suficiente agua", "Entrenamiento HIIT",
            "Comer despacio", "Planificar comidas", "Registrar progreso", "Dormir 7-8 horas"
        )
        else -> listOf( // Obesidad I, II y III
            "Déficit calórico", "Reducir azúcares", "Caminar 10k pasos",
            "Usar platos pequeños", "Comer despacio", "Incrementar fibra",
            "Beber suficiente agua", "Limitar alcohol", "Cocinar en casa", "Registrar progreso"
        )
    }

    val selectedOptions = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
                           .padding(15.dp)
                           .verticalScroll(scrollState)

    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Tus opciones para bajar de peso:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Según tu IMC: $resultado",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            maxItemsInEachRow = 1,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre filas
        ) {
            recomendaciones.forEach { option ->
                FilterChip(
                    modifier = Modifier.fillMaxWidth(), // Para que ocupe el ancho como una tarjeta
                    selected = selectedOptions.contains(option),
                    onClick = {
                        if (selectedOptions.contains(option)) {
                            selectedOptions.remove(option)
                        } else {
                            selectedOptions.add(option)
                        }
                    },

                    label = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = option,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                val listaCodificada = selectedOptions.joinToString(separator = ",")
                val destino = if (listaCodificada.isEmpty()) "vacio" else listaCodificada

                navController.navigate(Routes.screen_4 + "/${destino}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ver mis actividades",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Flecha derecha",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

}