package com.adamdawi.status_higherorlower.domain.model

sealed class ServerStatus {
    object Up : ServerStatus()
    //No internet or other client side problems
    object Unreachable : ServerStatus()
    data class Down(val reason: String) : ServerStatus()
}