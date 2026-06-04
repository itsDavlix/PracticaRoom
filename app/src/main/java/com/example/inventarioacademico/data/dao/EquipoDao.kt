package com.example.inventarioacademico.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.inventarioacademico.data.entity.Equipo
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos ORDER BY id DESC")
    fun obtenerEquipos(): Flow<List<Equipo>>

    @Query("SELECT * FROM equipos WHERE disponible = 1 ORDER BY nombre ASC")
    fun obtenerEquiposDisponibles(): Flow<List<Equipo>>

    @Insert
    suspend fun insertarEquipo(equipo: Equipo)

    @Update
    suspend fun actualizarEquipo(equipo: Equipo)

    @Delete
    suspend fun eliminarEquipo(equipo: Equipo)

    @Query("UPDATE equipos SET disponible = :disponible WHERE id = :equipoId")
    suspend fun actualizarDisponibilidad(equipoId: Int, disponible: Boolean)

    @Query("SELECT COUNT(*) FROM equipos")
    fun totalEquipos(): Flow<Int>

    @Query("SELECT COUNT(*) FROM equipos WHERE disponible = 1")
    fun totalDisponibles(): Flow<Int>

    @Query("SELECT COUNT(*) FROM equipos WHERE disponible = 0")
    fun totalPrestados(): Flow<Int>

    @Query("""
        SELECT categoria 
        FROM equipos 
        GROUP BY categoria 
        ORDER BY COUNT(*) DESC 
        LIMIT 1
    """)
    fun categoriaMayorCantidad(): Flow<String?>
}