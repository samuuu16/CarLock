package com.tuapp.carlock

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.*
import android.app.NotificationChannel
import android.app.NotificationManager
import kotlin.random.Random
import android.os.Build

object NotificationUtils {

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CocheChannel"
            val descriptionText = "Notificaciones de apertura/cierre del coche"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("coche_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun mostrarNotificacion(context: Context, mensaje: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val hasPermission = context.checkSelfPermission(permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasPermission) return // o muestra un mensaje, lanza un callback, etc.
        }

        val builder = NotificationCompat.Builder(context, "coche_channel")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Registro del coche")
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(Random.nextInt(), builder.build())
        }
    }

}
