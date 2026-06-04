package com.example.inventarioacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventarioacademico.data.database.InventarioDatabase
import com.example.inventarioacademico.data.repository.InventarioRepository
import com.example.inventarioacademico.ui.screens.AppInventario
import com.example.inventarioacademico.viewmodel.InventarioViewModel
import com.example.inventarioacademico.viewmodel.InventarioViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = InventarioDatabase.getDatabase(this)

        val repository = InventarioRepository(
            equipoDao = database.equipoDao(),
            prestamoDao = database.prestamoDao()
        )

        setContent {
            MaterialTheme {
                val viewModel: InventarioViewModel = viewModel(
                    factory = InventarioViewModelFactory(repository)
                )

                AppInventario(viewModel)
            }
        }
    }
}