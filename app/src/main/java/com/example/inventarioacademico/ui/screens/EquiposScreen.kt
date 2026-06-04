package com.example.inventarioacademico.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioacademico.data.entity.Equipo
import com.example.inventarioacademico.viewmodel.InventarioViewModel

@Composable
fun EquiposScreen(
    viewModel: InventarioViewModel,
    padding: PaddingValues
) {
    val equipos by viewModel.equipos.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var numeroSerie by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf("") }

    var equipoEditando by remember { mutableStateOf<Equipo?>(null) }

    val equiposFiltrados = if (busqueda.isBlank()) {
        equipos
    } else {
        equipos.filter {
            it.nombre.contains(busqueda, ignoreCase = true) ||
                    it.numeroSerie.contains(busqueda, ignoreCase = true) ||
                    it.categoria.contains(busqueda, ignoreCase = true) ||
                    it.marca.contains(busqueda, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Gestión de Equipos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del equipo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = numeroSerie,
            onValueChange = { numeroSerie = it },
            label = { Text("Número de serie") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (
                    nombre.isNotBlank() &&
                    categoria.isNotBlank() &&
                    marca.isNotBlank() &&
                    numeroSerie.isNotBlank()
                ) {
                    if (equipoEditando == null) {
                        viewModel.insertarEquipo(
                            Equipo(
                                nombre = nombre,
                                categoria = categoria,
                                marca = marca,
                                numeroSerie = numeroSerie
                            )
                        )
                    } else {
                        viewModel.actualizarEquipo(
                            equipoEditando!!.copy(
                                nombre = nombre,
                                categoria = categoria,
                                marca = marca,
                                numeroSerie = numeroSerie
                            )
                        )

                        equipoEditando = null
                    }

                    nombre = ""
                    categoria = ""
                    marca = ""
                    numeroSerie = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (equipoEditando == null) {
                    "Registrar equipo"
                } else {
                    "Actualizar equipo"
                }
            )
        }

        if (equipoEditando != null) {
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedButton(
                onClick = {
                    equipoEditando = null
                    nombre = ""
                    categoria = ""
                    marca = ""
                    numeroSerie = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar edición")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar por nombre, serie, marca o categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(equiposFiltrados) { equipo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Nombre: ${equipo.nombre}")
                        Text("Categoría: ${equipo.categoria}")
                        Text("Marca: ${equipo.marca}")
                        Text("Serie: ${equipo.numeroSerie}")

                        Text(
                            text = if (equipo.disponible) {
                                "Estado: Disponible"
                            } else {
                                "Estado: Prestado"
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Button(
                                onClick = {
                                    equipoEditando = equipo
                                    nombre = equipo.nombre
                                    categoria = equipo.categoria
                                    marca = equipo.marca
                                    numeroSerie = equipo.numeroSerie
                                }
                            ) {
                                Text("Editar")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            OutlinedButton(
                                onClick = {
                                    viewModel.eliminarEquipo(equipo)
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}