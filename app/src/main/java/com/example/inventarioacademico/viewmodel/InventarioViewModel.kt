package com.example.inventarioacademico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventarioacademico.data.entity.Equipo
import com.example.inventarioacademico.data.entity.Prestamo
import com.example.inventarioacademico.data.repository.InventarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventarioViewModel(
    private val repository: InventarioRepository
) : ViewModel() {

    val equipos = repository.equipos.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val equiposDisponibles = repository.equiposDisponibles.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val prestamos = repository.prestamosConEquipo.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val totalEquipos = repository.totalEquipos.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val totalDisponibles = repository.totalDisponibles.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val totalPrestados = repository.totalPrestados.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val categoriaMayorCantidad = repository.categoriaMayorCantidad.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    fun insertarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            repository.insertarEquipo(equipo)
        }
    }

    fun actualizarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            repository.actualizarEquipo(equipo)
        }
    }

    fun eliminarEquipo(equipo: Equipo) {
        viewModelScope.launch {
            repository.eliminarEquipo(equipo)
        }
    }

    fun registrarPrestamo(prestamo: Prestamo) {
        viewModelScope.launch {
            repository.registrarPrestamo(prestamo)
        }
    }

    fun registrarDevolucion(prestamoId: Int, equipoId: Int, fecha: String) {
        viewModelScope.launch {
            repository.registrarDevolucion(prestamoId, equipoId, fecha)
        }
    }
}