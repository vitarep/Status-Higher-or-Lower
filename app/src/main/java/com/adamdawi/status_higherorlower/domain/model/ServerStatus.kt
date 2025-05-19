package com.adamdawi.status_higherorlower.domain.model

sealed class ServerStatus {
    object Up : ServerStatus()
    data class Down(val reason: String) : ServerStatus()
}