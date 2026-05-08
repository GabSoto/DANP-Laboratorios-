package com.example.healthplanner

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Screen_4(
    navController: NavController,
    listaActividades: List<String>
) {
    var nextId by remember { mutableLongStateOf(1L) }
    var misTareas by remember {
        mutableStateOf(
            listaActividades.map { texto ->
                Task(id = nextId++, title = texto)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Mis tareas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = PurplePrimary
        )

        Spacer(modifier = Modifier.height(20.dp))

        CardCreateForm(onAddTask = { newTitle ->
            val newTask = Task(id = nextId, title = newTitle)
            misTareas = misTareas + newTask
            nextId++
        })

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )  {
            items(
                items = misTareas,
                key = { it.id }
            ) { task ->
                CardTask(
                    task = task,
                    onDelete = {
                        misTareas = misTareas.filter { it.id != task.id }
                    },
                    onEdit = { newName ->
                        misTareas = misTareas.map {
                            if (it.id == task.id) it.copy(title = newName) else it
                        }
                    }

                )
            }
        }
    }
}

@Composable
fun CardCreateForm(
    onAddTask: (String) -> Unit)
{
    var newTask by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Crea una nueva tarea!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = PurplePrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label =  { Text(" Nombre de la Tarea") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = PurplePrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        onAddTask(newTask)
                        newTask = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear", color = Color.White)
            }
        }
    }
}

@Composable
fun CardTask(
    task: Task,
    onDelete: () -> Unit,
    onEdit: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(task.title) }

    var isDone by remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isDone) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant,
        label = "ColorAnimation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (isEditing) {
                        TextField(
                            value = editedName,
                            onValueChange = { editedName = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    } else {
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isDone) Color(0xFF2E7D32) else PurplePrimary,
                            textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
                        )
                    }
                }

                Checkbox(
                    checked = isDone,
                    onCheckedChange = { isDone = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4CAF50)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
            ) {
                if (isEditing) {
                    Button(
                        onClick = {
                            onEdit(editedName)
                            isEditing = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = { isEditing = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                    ) {
                        Text("Editar", color = Color.White)
                    }
                }

                Button(
                    onClick = {
                        if (isEditing) isEditing = false else onDelete()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isEditing) Color.Gray else Color.Red
                    )
                ) {
                    Text(if (isEditing) "Cancelar" else "Eliminar", color = Color.White)
                }
            }
        }
    }
}