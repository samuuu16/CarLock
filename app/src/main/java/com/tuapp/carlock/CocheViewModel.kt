package com.tuapp.carlock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.tuapp.carlock.EstadoCoche
import com.tuapp.carlock.Registro

class CocheViewModel(application: Application) : AndroidViewModel(application) {

    // ✅ Inicialización diferida con lazy para evitar crash
    private val db by lazy {
        AppDatabase.getDatabase(application)
    }

    private val dao by lazy {
        db.registroDao()
    }

    private val _estado = MutableStateFlow(EstadoCoche.CERRADO)
    val estado: StateFlow<EstadoCoche> = _estado

    val historial: Flow<List<Registro>> by lazy {
        dao.obtenerTodos()
    }

    fun abrirCoche() {
        _estado.value = EstadoCoche.ABIERTO
        guardarAccion("ABRIR")
    }

    fun cerrarCoche() {
        _estado.value = EstadoCoche.CERRADO
        guardarAccion("CERRAR")
    }

    private fun guardarAccion(accion: String) {
        viewModelScope.launch {
            dao.insertar(Registro(accion = accion))
        }
    }
}
