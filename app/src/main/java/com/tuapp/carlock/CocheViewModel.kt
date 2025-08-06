package com.tuapp.carlock

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CocheViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val db: AppDatabase by lazy {
        requireNotNull(context) { "Application context is null en ViewModel" }
        AppDatabase.getDatabase(context)
    }

    private val dao: RegistroDao by lazy {
        db.registroDao()
    }

    private val _estado = MutableStateFlow(EstadoCoche.CERRADO)
    val estado: StateFlow<EstadoCoche> = _estado

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
        NotificationUtils.mostrarNotificacionCocheAbierto(context)
    }

    fun cerrarCoche() {
        _estado.value = EstadoCoche.CERRADO
        guardarAccion("CERRAR")
        NotificationUtils.mostrarNotificacionCocheCerrado(context)
    }

    private fun guardarAccion(accion: String) {
        viewModelScope.launch {
            dao.insertar(Registro(accion = accion))
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
