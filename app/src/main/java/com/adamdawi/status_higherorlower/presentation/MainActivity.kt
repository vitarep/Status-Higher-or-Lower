package com.adamdawi.status_higherorlower.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.adamdawi.status_higherorlower.ServerStatusScheduler
import com.adamdawi.status_higherorlower.presentation.ui.theme.StatusHigherOrLowerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("MainActivity", "POST_NOTIFICATIONS permission granted")
        } else {
            Log.w("MainActivity", "POST_NOTIFICATIONS permission denied")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
        ServerStatusScheduler.scheduleWorker(applicationContext)
        enableEdgeToEdge()

        setContent {
            StatusHigherOrLowerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ServerStatusScreen(
                        viewModel = koinViewModel(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}