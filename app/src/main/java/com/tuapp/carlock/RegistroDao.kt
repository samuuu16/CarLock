package com.tuapp.carlock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {
    @Insert
    suspend fun insertar(registro: Registro)

    @Query("SELECT * FROM registros ORDER BY timestamp DESC")
    fun obtenerTodos(): Flow<List<Registro>>

    @Query("DELETE FROM registros")
    suspend fun borrarTodos()
}
