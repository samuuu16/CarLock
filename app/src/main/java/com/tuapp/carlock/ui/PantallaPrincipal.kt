package com.tuapp.carlock.ui

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
import androidx.compose.ui.graphics.Color
import com.tuapp.carlock.EstadoCoche
import com.tuapp.carlock.CocheViewModel
import com.tuapp.carlock.NotificationUtils.mostrarNotificacion
import androidx.compose.material3.MaterialTheme as Material3Theme

@Composable
fun PantallaPrincipal(
    cocheViewModel: CocheViewModel,
    onHistorialClick: () -> Unit
) {
    val estado by cocheViewModel.estado.collectAsState()
    val context = LocalContext.current

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
            style = Material3Theme.typography.titleLarge,
            color = Material3Theme.colorScheme.onBackground
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
        Button(onClick = { onHistorialClick() }) {
            Text("HISTORIAL")
        }
    }
}
