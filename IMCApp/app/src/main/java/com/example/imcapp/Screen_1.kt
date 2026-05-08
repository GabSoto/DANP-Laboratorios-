package com.example.imcapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imcapp.Routes

val PurplePrimary = Color(0xFF5D4099)
val LightCardColor = Color(0xFFF4F3F8)

@Composable
fun Screen_1(navController: NavController) {
    var isMale by remember { mutableStateOf(true) }
    var height by remember { mutableFloatStateOf(175f) }
    var weight by remember { mutableIntStateOf(70) }
    var age by remember { mutableIntStateOf(25) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Indice Masa Corporal",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Ingrese y seleccione la siguiente informacion",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            GenderCard(
                modifier = Modifier.weight(1f),
                label = "Hombre",
                icon = Icons.Default.Man,
                isSelected = isMale,
                onClick = { isMale = true }
            )
            GenderCard(
                modifier = Modifier.weight(1f),
                label = "Mujer",
                icon = Icons.Default.Woman,
                isSelected = !isMale,
                onClick = { isMale = false }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightCardColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text("Altura", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = height.toInt().toString(),
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary
                        )
                        Text("cm", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
                Slider(
                    value = height,
                    onValueChange = { height = it },
                    valueRange = 100f..220f,
                    colors = SliderDefaults.colors(
                        thumbColor = PurplePrimary,
                        activeTrackColor = PurplePrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ControlCard(
                modifier = Modifier.weight(1f),
                label = "Peso(kg)",
                value = weight,
                onValueChange = { weight = it }
            )
            ControlCard(
                modifier = Modifier.weight(1f),
                label = "Edad",
                value = age,
                onValueChange = { age = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val heightInMeters = height / 100f
                val calculo = weight / (heightInMeters * heightInMeters)

                navController.navigate(Routes.screenB + "/${calculo.toInt()}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("CALCULAR", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun GenderCard(
    modifier: Modifier,
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PurplePrimary else LightCardColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(48.dp),
                tint = if (isSelected) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ControlCard(
    modifier: Modifier,
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Card(
        modifier = modifier.height(160.dp),
        colors = CardDefaults.cardColors(containerColor = LightCardColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.Gray, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.Start))
            Text(text = value.toString(), fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { if (value > 0) onValueChange(value - 1) },
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Menos")
                }
                IconButton(
                    onClick = { onValueChange(value + 1) },
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Más")
                }
            }
        }
    }
}