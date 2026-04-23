package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.ToDoAppTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var misTareas by remember { mutableStateOf(listOf<Task>()) }

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
            color = Color(0xFF1976D2)
        )

        Spacer(modifier = Modifier.height(20.dp))

        CardCreateForm( onAddTask = { newTittle ->
            misTareas = misTareas + Task(title = newTittle)
        })

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )  {
            items(items = misTareas, key = { it.id }) { task ->
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

        // CardTask("Mi primera tarea")
    }
}


data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String
)
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
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label =  { Text(" nombre de la tarea ...") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.Blue
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        Column (
            modifier = Modifier.padding(5.dp)
        ) {
            Spacer(modifier = Modifier.width(7.dp))

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
                    modifier = Modifier.padding(10.dp),
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.width(7.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.End),
            ) {
                if (isEditing) {
                    // Boton para Guardar cambios
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
                    // Boton para entrar en modo Edición
                    Button(
                        onClick = { isEditing = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
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