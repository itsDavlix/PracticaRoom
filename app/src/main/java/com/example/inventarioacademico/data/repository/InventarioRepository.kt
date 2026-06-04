package com.example.inventarioacademico.data.repository

import com.example.inventarioacademico.data.dao.EquipoDao
import com.example.inventarioacademico.data.dao.PrestamoDao
import com.example.inventarioacademico.data.entity.Equipo
import com.example.inventarioacademico.data.entity.Prestamo

class InventarioRepository(
    private val equipoDao: EquipoDao,
    private val prestamoDao: PrestamoDao
) {
    val equipos = equipoDao.obtenerEquipos()
    val equiposDisponibles = equipoDao.obtenerEquiposDisponibles()
    val prestamosConEquipo = prestamoDao.obtenerPrestamosConEquipo()

    val totalEquipos = equipoDao.totalEquipos()
    val totalDisponibles = equipoDao.totalDisponibles()
    val totalPrestados = equipoDao.totalPrestados()
    val categoriaMayorCantidad = equipoDao.categoriaMayorCantidad()

    suspend fun insertarEquipo(equipo: Equipo) {
        equipoDao.insertarEquipo(equipo)
    }

    suspend fun actualizarEquipo(equipo: Equipo) {
        equipoDao.actualizarEquipo(equipo)
    }

    suspend fun eliminarEquipo(equipo: Equipo) {
        equipoDao.eliminarEquipo(equipo)
    }

    suspend fun registrarPrestamo(prestamo: Prestamo) {
        prestamoDao.insertarPrestamo(prestamo)
        equipoDao.actualizarDisponibilidad(prestamo.equipoId, false)
    }

    suspend fun registrarDevolucion(prestamoId: Int, equipoId: Int, fecha: String) {
        prestamoDao.registrarDevolucion(prestamoId, fecha)
        equipoDao.actualizarDisponibilidad(equipoId, true)
    }
}