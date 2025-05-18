package com.adamdawi.status_higherorlower.data.remote

import com.adamdawi.status_higherorlower.BuildConfig
import com.adamdawi.status_higherorlower.data.ServerStatusResponse
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import com.adamdawi.status_higherorlower.domain.model.ServerStatus
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ServerStatusRepositoryImpl(
    private val httpClient: HttpClient
) : ServerStatusRepository {
    override suspend fun getStatus(): ServerStatus {
        return try {
            val response: ServerStatusResponse = httpClient.get(BuildConfig.SERVER_URL).body()
            when (response.status) {
                "UP" -> ServerStatus.Up
                else -> ServerStatus.Down(response.status)
            }
        } catch (e: Exception) {
            ServerStatus.Unreachable
        }
    }
}