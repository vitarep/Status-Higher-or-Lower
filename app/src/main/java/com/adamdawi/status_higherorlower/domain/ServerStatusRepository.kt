package com.adamdawi.status_higherorlower.domain

import com.adamdawi.status_higherorlower.domain.model.ServerStatus

interface ServerStatusRepository {
    suspend fun getStatus(): ServerStatus
}