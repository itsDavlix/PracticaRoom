package com.example.inventarioacademico.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioacademico.data.entity.Prestamo
import com.example.inventarioacademico.viewmodel.InventarioViewModel

@Composable
fun PrestamosScreen(
    viewModel: InventarioViewModel,
    padding: PaddingValues
) {
    val equiposDisponibles by viewModel.equiposDisponibles.collectAsState()

    var solicitante by remember { mutableStateOf("") }
    var fechaPrestamo by remember { mutableStateOf("") }
    var equipoSeleccionadoId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Registrar Préstamo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = solicitante,
            onValueChange = { solicitante = it },
            label = { Text("Nombre del solicitante") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fechaPrestamo,
            onValueChange = { fechaPrestamo = it },
            label = { Text("Fecha de préstamo") },
            placeholder = { Text("Ejemplo: 03/06/2026") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Seleccione un equipo disponible:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (equiposDisponibles.isEmpty()) {
            Text("No hay equipos disponibles para préstamo.")
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(equiposDisponibles) { equipo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            RadioButton(
                                selected = equipoSeleccionadoId == equipo.id,
                                onClick = {
                                    equipoSeleccionadoId = equipo.id
                                }
                            )

                            Column(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text("Equipo: ${equipo.nombre}")
                                Text("Categoría: ${equipo.categoria}")
                                Text("Serie: ${equipo.numeroSerie}")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (
                    solicitante.isNotBlank() &&
                    fechaPrestamo.isNotBlank() &&
                    equipoSeleccionadoId != null
                ) {
                    viewModel.registrarPrestamo(
                        Prestamo(
                            equipoId = equipoSeleccionadoId!!,
                            solicitante = solicitante,
                            fechaPrestamo = fechaPrestamo
                        )
                    )

                    solicitante = ""
                    fechaPrestamo = ""
                    equipoSeleccionadoId = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar préstamo")
        }
    }
}