package com.tuapp.carlock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.tuapp.carlock.NotificationUtils.createNotificationChannel
import com.tuapp.carlock.NotificationUtils.mostrarNotificacion

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        // ✅ Corrección: fábrica para AndroidViewModel con Application
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        val cocheViewModel = ViewModelProvider(this, factory)[CocheViewModel::class.java]

        setContent {
            PantallaPrincipal(cocheViewModel = cocheViewModel)
        }
    }
}

@Composable
fun PantallaPrincipal(cocheViewModel: CocheViewModel) {
    val estado by cocheViewModel.estado.collectAsState()
    val context = LocalContext.current

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (estado == EstadoCoche.CERRADO) Icons.Default.Lock else Icons.Default.LockOpen,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (estado == EstadoCoche.CERRADO) "El coche está cerrado" else "El coche está abierto",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Button(onClick = {
                    cocheViewModel.abrirCoche()
                    mostrarNotificacion(context, "🔓 El coche se ha abierto")
                }) {
                    Text("ABRIR")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    cocheViewModel.cerrarCoche()
                    mostrarNotificacion(context, "🔒 El coche se ha cerrado")
                }) {
                    Text("CERRAR")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { /* Aquí pondrías navegación al historial */ }) {
                Text("HISTORIAL")
            }
        }
    }
}
