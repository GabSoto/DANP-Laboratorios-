package com.example.imcapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScreenB(
    resultado: Float,
    onRecalculate: () -> Unit
) {

    val (statusText, statusColor) = when {
        resultado < 18.5f -> "BAJO PESO" to Color(0xFF2196F3)
        resultado < 25.0f -> "NORMAL" to Color(0xFF4CAF50)
        resultado < 30.0f -> "SOBREPESO" to Color(0xFFFFC107)
        resultado < 35.0f -> "OBESIDAD I" to Color(0xFFFF9800)
        resultado < 40.0f -> "OBESIDAD II" to Color(0xFFFF5722)
        else -> "OBESIDAD III" to Color(0xFFB71C1C)
    }
    /*
    Text(
        text = "Resultado es: $resultado",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
    */

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RESULTADOS",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "IMC = $resultado",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = statusText,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = statusColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Se encuentra en los valores de $statusText",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { onRecalculate() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE)
            )
        ) {
            Text(
                text = "RECALCULAR",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}