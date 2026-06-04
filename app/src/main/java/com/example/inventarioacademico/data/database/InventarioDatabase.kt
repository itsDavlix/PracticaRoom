package com.example.inventarioacademico.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventarioacademico.data.dao.EquipoDao
import com.example.inventarioacademico.data.dao.PrestamoDao
import com.example.inventarioacademico.data.entity.Equipo
import com.example.inventarioacademico.data.entity.Prestamo

@Database(
    entities = [Equipo::class, Prestamo::class],
    version = 1,
    exportSchema = false
)
abstract class InventarioDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao
    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile
        private var INSTANCE: InventarioDatabase? = null

        fun getDatabase(context: Context): InventarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventarioDatabase::class.java,
                    "inventario_academico_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}