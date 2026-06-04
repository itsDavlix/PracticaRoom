package com.example.inventarioacademico.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventarioacademico.viewmodel.InventarioViewModel

@Composable
fun DashboardScreen(
    viewModel: InventarioViewModel,
    padding: PaddingValues
) {
    val totalEquipos by viewModel.totalEquipos.collectAsState()
    val disponibles by viewModel.totalDisponibles.collectAsState()
    val prestados by viewModel.totalPrestados.collectAsState()
    val categoriaMayor by viewModel.categoriaMayorCantidad.collectAsState()

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardDashboard("Total de equipos", totalEquipos.toString())

        Spacer(modifier = Modifier.height(8.dp))

        CardDashboard("Equipos disponibles", disponibles.toString())

        Spacer(modifier = Modifier.height(8.dp))

        CardDashboard("Equipos prestados", prestados.toString())

        Spacer(modifier = Modifier.height(8.dp))

        CardDashboard(
            "Categoría con más equipos",
            categoriaMayor ?: "Sin datos"
        )
    }
}

@Composable
fun CardDashboard(
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}