package com.tuapp.carlock


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "registros")
data class Registro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accion: String, // "ABRIR" o "CERRAR"
    val timestamp: Long = System.currentTimeMillis()
)
