package com.adamdawi.status_higherorlower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ServerStatusViewModel : ViewModel() {

    private val _status = MutableStateFlow("Loading...")
    val status = _status.asStateFlow()

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    init {
        fetchStatus()
    }

    fun fetchStatus() {
        viewModelScope.launch {
            try {
                val response: ServerStatusResponse = client.get(BuildConfig.SERVER_URL).body()
                _status.value = if (response.status == "UP") "🟢 Server is UP" else "🔴 Server is DOWN\n ${response.status}"
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = "⚠️ Cannot reach server"
            }
        }
    }
}
