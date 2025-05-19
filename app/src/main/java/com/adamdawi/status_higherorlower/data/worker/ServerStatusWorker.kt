package com.adamdawi.status_higherorlower.data.worker

import android.R.drawable.ic_dialog_alert
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adamdawi.status_higherorlower.MainActivity
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import com.adamdawi.status_higherorlower.domain.model.ServerStatus

class ServerStatusWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: ServerStatusRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val response = repository.getStatus()
            when (response) {
                ServerStatus.Up -> {
                    Result.success()
                }
                is ServerStatus.Down -> {
                    showNotification("The server is down.")
                    Result.success()
                }
                ServerStatus.Unreachable-> {
                    Result.success()
                }
            }
        } catch (e: Exception) {
            Log.e("Worker error", "Unexpected error: ${e.message}", e)
            showNotification("The server is unreachable. Unexpected error in worker.")
            Result.success()
        }
    }

    private fun showNotification(message: String) {
        val channelId = "server_status_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Android 8+ requires notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Server Status",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Server Status Alert")
            .setContentText(message)
            .setSmallIcon(ic_dialog_alert)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
