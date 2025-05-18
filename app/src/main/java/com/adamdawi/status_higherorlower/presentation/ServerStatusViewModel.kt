package com.adamdawi.status_higherorlower.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adamdawi.status_higherorlower.domain.model.ServerStatus
import com.adamdawi.status_higherorlower.domain.ServerStatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ServerStatusViewModel(
    private val repository: ServerStatusRepository
) : ViewModel() {

    private val _status = MutableStateFlow("Loading...")
    val status = _status.asStateFlow()

    init {
        fetchStatus()
    }

    fun fetchStatus() {
        viewModelScope.launch {
            when (val result = repository.getStatus()) {
                is ServerStatus.Up -> _status.value = "🟢 Server is UP"
                is ServerStatus.Down -> _status.value = "🔴 Server is DOWN\n${result.reason}"
                ServerStatus.Unreachable -> _status.value = "⚠️ Cannot reach server"
            }
        }
    }
}