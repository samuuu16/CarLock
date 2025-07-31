package com.tuapp.carlock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


class CocheViewModel(application: Application) : AndroidViewModel(application) {

    // 💡 Protección contra contextos nulos
    private val db: AppDatabase by lazy {
        val context = getApplication<Application>()
        requireNotNull(context) { "Application context is null en ViewModel" }
        AppDatabase.getDatabase(context)
    }

    private val dao: RegistroDao by lazy {
        db.registroDao()
    }

    // Estado actual del coche
    private val _estado = MutableStateFlow(EstadoCoche.CERRADO)
    val estado: StateFlow<EstadoCoche> = _estado

    // Historial en memoria reactiva (MODIFICABLE)
    private val _historial = MutableStateFlow<List<Registro>>(emptyList())
    val historial: StateFlow<List<Registro>> = _historial

    init {
        viewModelScope.launch {
            dao.obtenerTodos().collect {
                _historial.value = it
            }
        }
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

            // Recargar historial
            dao.obtenerTodos().collect {
                _historial.value = it
            }
        }
    }


    fun clearHistorial() {
        viewModelScope.launch {
            dao.borrarTodos()
            _historial.value = emptyList()
        }
    }
}
