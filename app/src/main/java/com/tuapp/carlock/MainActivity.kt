package com.tuapp.carlock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.tuapp.carlock.ui.PantallaPrincipal
import com.tuapp.carlock.ui.PantallaHistorial
import com.tuapp.carlock.ui.theme.CarLockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NotificationUtils.createNotificationChannel(this)

        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        val cocheViewModel = ViewModelProvider(this, factory)[CocheViewModel::class.java]

        setContent {
            CarLockTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "principal") {
                    composable("principal") {
                        PantallaPrincipal(
                            cocheViewModel = cocheViewModel,
                            onHistorialClick = { navController.navigate("historial") }
                        )
                    }
                    composable("historial") {
                        PantallaHistorial(
                            viewModel = cocheViewModel,
                            onVolver = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
