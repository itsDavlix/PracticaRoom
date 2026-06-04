package com.example.inventarioacademico.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.inventarioacademico.viewmodel.InventarioViewModel

@Composable
fun AppInventario(viewModel: InventarioViewModel) {
    var pantalla by remember { mutableStateOf("dashboard") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = pantalla == "dashboard",
                    onClick = { pantalla = "dashboard" },
                    label = { Text("Inicio") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = pantalla == "equipos",
                    onClick = { pantalla = "equipos" },
                    label = { Text("Equipos") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = pantalla == "prestamos",
                    onClick = { pantalla = "prestamos" },
                    label = { Text("Préstamos") },
                    icon = {}
                )

                NavigationBarItem(
                    selected = pantalla == "historial",
                    onClick = { pantalla = "historial" },
                    label = { Text("Historial") },
                    icon = {}
                )
            }
        }
    ) { padding: PaddingValues ->
        when (pantalla) {
            "dashboard" -> DashboardScreen(viewModel, padding)
            "equipos" -> EquiposScreen(viewModel, padding)
            "prestamos" -> PrestamosScreen(viewModel, padding)
            "historial" -> HistorialScreen(viewModel, padding)
        }
    }
}