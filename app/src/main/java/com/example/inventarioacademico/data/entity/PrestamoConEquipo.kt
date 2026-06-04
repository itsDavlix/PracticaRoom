package com.example.inventarioacademico.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PrestamoConEquipo(
    @Embedded val prestamo: Prestamo,

    @Relation(
        parentColumn = "equipoId",
        entityColumn = "id"
    )
    val equipo: Equipo
)