package com.adamdawi.status_higherorlower.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ServerStatusScheduler {
    fun scheduleWorker(context: Context, intervalMinutes: Long = 15) {
        val workRequest = PeriodicWorkRequestBuilder<ServerStatusWorker>(
            intervalMinutes, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "server_status_check",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
