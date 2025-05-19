package com.adamdawi.status_higherorlower.data.worker

import android.R.drawable.ic_dialog_alert
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import com.adamdawi.status_higherorlower.domain.model.ServerStatus
import java.net.UnknownHostException

class ServerStatusWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: ServerStatusRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val response = repository.getStatus()
            if (response == ServerStatus.Up) {
                Result.success()
            } else {
                showNotification("The server is down.")
                Result.success()
            }
        } catch (_: UnknownHostException) {
            Result.success()
        } catch (_: Exception) {
            showNotification("The server is unreachable.")
            Result.success()
        }
    }

    private fun showNotification(message: String) {
        val channelId = "server_status_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
            .build()

        notificationManager.notify(1, notification)
    }
}
