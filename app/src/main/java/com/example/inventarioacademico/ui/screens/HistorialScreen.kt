package com.example.inventarioacademico.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioacademico.viewmodel.InventarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(
    viewModel: InventarioViewModel,
    padding: PaddingValues
) {
    val prestamos by viewModel.prestamos.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de Préstamos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (prestamos.isEmpty()) {
            Text("Todavía no hay préstamos registrados.")
        } else {
            LazyColumn {
                items(prestamos) { item ->
                    val prestamo = item.prestamo
                    val equipo = item.equipo

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text("Equipo: ${equipo.nombre}")
                            Text("Categoría: ${equipo.categoria}")
                            Text("Solicitante: ${prestamo.solicitante}")
                            Text("Fecha préstamo: ${prestamo.fechaPrestamo}")
                            Text("Fecha devolución: ${prestamo.fechaDevolucion ?: "Pendiente"}")

                            Text(
                                text = if (prestamo.fechaDevolucion == null) {
                                    "Estado: Prestado"
                                } else {
                                    "Estado: Devuelto"
                                }
                            )

                            if (prestamo.fechaDevolucion == null) {
                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        val fechaActual = SimpleDateFormat(
                                            "dd/MM/yyyy",
                                            Locale.getDefault()
                                        ).format(Date())

                                        viewModel.registrarDevolucion(
                                            prestamoId = prestamo.id,
                                            equipoId = equipo.id,
                                            fecha = fechaActual
                                        )
                                    }
                                ) {
                                    Text("Registrar devolución")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}