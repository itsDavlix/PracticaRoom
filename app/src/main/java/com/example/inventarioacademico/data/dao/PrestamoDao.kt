package com.example.inventarioacademico.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.inventarioacademico.data.entity.Prestamo
import com.example.inventarioacademico.data.entity.PrestamoConEquipo
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {

    @Insert
    suspend fun insertarPrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos ORDER BY id DESC")
    fun obtenerPrestamos(): Flow<List<Prestamo>>

    @Transaction
    @Query("SELECT * FROM prestamos ORDER BY id DESC")
    fun obtenerPrestamosConEquipo(): Flow<List<PrestamoConEquipo>>

    @Query("""
        UPDATE prestamos 
        SET fechaDevolucion = :fechaDevolucion 
        WHERE id = :prestamoId
    """)
    suspend fun registrarDevolucion(prestamoId: Int, fechaDevolucion: String)
}