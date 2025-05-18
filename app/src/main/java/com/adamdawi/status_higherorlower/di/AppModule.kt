package com.adamdawi.status_higherorlower.di

import com.adamdawi.status_higherorlower.data.remote.ServerStatusRepositoryImpl
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import com.adamdawi.status_higherorlower.presentation.ServerStatusViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    single<ServerStatusRepository> { ServerStatusRepositoryImpl(get()) }

    viewModel { ServerStatusViewModel(get()) }
}