package com.tuapp.carlock.ui

import android.content.Context
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
import androidx.compose.ui.unit.sp
import com.tuapp.carlock.EstadoCoche
import com.tuapp.carlock.CocheViewModel
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
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (estado == EstadoCoche.CERRADO) Icons.Default.Lock else Icons.Default.LockOpen,
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .padding(8.dp),
            tint = Material3Theme.colorScheme.primary
        )

        Text(
            text = if (estado == EstadoCoche.CERRADO) "El coche está cerrado" else "El coche está abierto",
            style = Material3Theme.typography.titleLarge,
            color = Material3Theme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { cocheViewModel.abrirCoche() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Material3Theme.colorScheme.primary
                )
            ) {
                Text("ABRIR", fontSize = 18.sp)
            }

            Button(
                onClick = { cocheViewModel.cerrarCoche() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Material3Theme.colorScheme.primary
                )
            ) {
                Text("CERRAR", fontSize = 18.sp)
            }
        }

        Button(
            onClick = { onHistorialClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Material3Theme.colorScheme.secondary
            )
        ) {
            Text("HISTORIAL", fontSize = 18.sp)
        }
    }
}
