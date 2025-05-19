package com.adamdawi.status_higherorlower.data.remote

import android.util.Log
import com.adamdawi.status_higherorlower.BuildConfig
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import com.adamdawi.status_higherorlower.domain.model.ServerStatus
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import java.net.ConnectException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

class ServerStatusRepositoryImpl(
    private val httpClient: HttpClient
) : ServerStatusRepository {

    companion object {
        private const val TAG = "ServerStatusRepo"
    }

    override suspend fun getStatus(): ServerStatus {
        return try {
            val response: HttpResponse = httpClient.get(BuildConfig.SERVER_URL)
            val statusCode = response.status
            Log.d(TAG, "Server response code: ${statusCode.value} ${statusCode.description}")

            if (statusCode.value in 200..299) {
                ServerStatus.Up
            } else {
                ServerStatus.Down("HTTP ${statusCode.value} ${statusCode.description}")
            }
        } catch (e: UnknownHostException) {
            Log.e("Network", "Unknown host: ${e.message}")
            ServerStatus.Unreachable
        } catch (e: UnresolvedAddressException) {
            Log.e("Network", "Unresolved address: ${e.message}")
            ServerStatus.Unreachable
        } catch (e: ConnectException) {
            Log.e("Network", "Connection refused: ${e.message}")
            ServerStatus.Unreachable
        }catch (e: Exception) {
            Log.e(TAG, "Error contacting server", e)
            ServerStatus.Down("Exception: ${e.message}")
        }
    }
}