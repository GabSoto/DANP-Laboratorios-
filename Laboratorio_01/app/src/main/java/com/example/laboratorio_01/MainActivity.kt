package com.example.laboratorio_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import com.example.laboratorio_01.ui.theme.Laboratorio_01Theme // Para el listOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio_01Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EjercicioTres()
                }
            }
        }
    }
}


// PRIMER EJERCICIO
@Composable
fun EjercicioUno() {
    var mensaje by remember { mutableStateOf("¡Presiona el botón de abajo!") }
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra todo horizontalmente
        verticalArrangement = Arrangement.Center            // Centra todo verticalmente
    ) {
        // 1. TÍTULO
        Text(
            text = "PRIMER EJERCICIO",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio en blanco

        // Texto mutable
        Text(
            text = mensaje,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. BOTÓN
        Button(
            onClick = {
                count++

                // Al hacer clic, cambiamos el valor de la variable
                mensaje = "¡Genial! Has presionado el botón correctamente x$count"
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Haz clic aquí", color = Color.White)
        }
    }
}

// SEGUNDO EJERCICIO
@Composable
fun CursoCard(nombre: String, profesor: String, grupo: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Profesor: $profesor",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Grupo: $grupo",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun EjercicioDos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Mis Cursos Reutilizables",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CursoCard(
            nombre = "SEGURIDAD INFORMATICA",
            profesor = "Ing. Luis Alberto",
            grupo = "B"
        )

        CursoCard(
            nombre = "PROYECTO DE INGENIERIA DE SOFTWARE 1",
            profesor = "Ing. Maria Garcia",
            grupo = "B"
        )

        CursoCard(
            nombre = "DESARROLLO AVANZADO EN NUEVAS PLATAFORMAS (E)",
            profesor = "Ing. Carlos Perez",
            grupo = "B"
        )
    }
}

// TERCER EJERCICIO
@Composable
fun EjercicioTres() {
    val listaCursos = listOf(
        listOf("SEGURIDAD INFORMATICA", "Ing. Luis Alberto", "B"),
        listOf("PROYECTO DE INGENIERIA DE SOFTWARE 1", "Ing. Maria Garcia", "B"),
        listOf("DESARROLLO AVANZADO EN NUEVAS PLATAFORMAS (E)", "Ing. Carlos Perez", "B"),
        listOf("SISTEMAS OPERATIVOS", "Ing. Roberto Sanchez", "B"),
        listOf("BASE DE DATOS II", "Ing. Ana Torres", "B")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "EJERCICIO TRES: LazyColumn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaCursos) { curso ->
                CursoCard(
                    nombre = curso[0],
                    profesor = curso[1],
                    grupo = curso[2]
                )
            }
        }
    }
}