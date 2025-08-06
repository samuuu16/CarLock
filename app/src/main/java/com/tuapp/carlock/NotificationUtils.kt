package com.tuapp.carlock

import android.content.Context
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {

    private const val CHANNEL_ID = "coche_channel"
    private const val NOTIFICATION_ID_CIERRE = 1001
    private const val NOTIFICATION_ID_APERTURA = 1002

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CocheChannel"
            val descriptionText = "Notificaciones de apertura/cierre del coche"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun mostrarNotificacionCocheCerrado(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val hasPermission = context.checkSelfPermission(permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasPermission) return
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Registro del coche")
            .setContentText("El coche se ha cerrado 🔒")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_CIERRE, builder.build())
    }


    fun mostrarNotificacionCocheAbierto(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val hasPermission = context.checkSelfPermission(permission) == android.content.pm.PackageManager.PERMISSION_GRANTED
            if (!hasPermission) return
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Registro del coche")
            .setContentText("¡Coche abierto con éxito! 🔓")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_APERTURA, builder.build())
    }
}
