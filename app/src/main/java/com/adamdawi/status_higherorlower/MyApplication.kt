package com.adamdawi.status_higherorlower

import android.app.Application
import androidx.work.Configuration
import com.adamdawi.status_higherorlower.data.KoinWorkerFactory
import com.adamdawi.status_higherorlower.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}