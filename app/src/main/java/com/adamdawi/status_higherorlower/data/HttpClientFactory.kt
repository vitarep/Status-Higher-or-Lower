package com.adamdawi.status_higherorlower.data

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.adamdawi.status_higherorlower.ServerStatusWorker
import org.koin.java.KoinJavaComponent.getKoin

class KoinWorkerFactory : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ServerStatusWorker::class.qualifiedName -> {
                ServerStatusWorker(
                    appContext,
                    workerParameters,
                    getKoin().get()
                )
            }
            else -> null
        }
    }
}