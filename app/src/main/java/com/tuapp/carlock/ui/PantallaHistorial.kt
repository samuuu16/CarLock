package com.tuapp.carlock.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tuapp.carlock.CocheViewModel
import com.tuapp.carlock.Registro
import java.text.SimpleDateFormat
import java.util.*


fun formatearTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}


@Composable
fun PantallaHistorial(viewModel: CocheViewModel) {
    val registros by viewModel.historial.collectAsState(initial = emptyList())

    LazyColumn {
        items(registros) { registro ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Acción: ${registro.accion}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Fecha: ${formatearTimestamp(registro.timestamp)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
