package com.adamdawi.status_higherorlower.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adamdawi.status_higherorlower.presentation.ui.theme.StatusHigherOrLowerTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServerStatusScreen(
    viewModel: ServerStatusViewModel = koinViewModel()
) {
    val status = viewModel.status.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ServerStatusScreenContent(
            modifier = Modifier.padding(innerPadding),
            status = status.value
        )
    }
}

@Composable
fun ServerStatusScreenContent(
    modifier: Modifier = Modifier,
    status: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = status,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
private fun ServerStatusScreenPreview() {
    StatusHigherOrLowerTheme {
        ServerStatusScreenContent(
            status = "🟢 Server is UP"
        )
    }
}