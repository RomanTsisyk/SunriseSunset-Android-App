package io.github.romantsisyk.myapplication.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.myapplication.presentation.viewmodel.SunriseSunsetViewModel

@Composable
fun MainScreen(viewModel: SunriseSunsetViewModel) {
    val state by viewModel.state.collectAsState()

    // Ensure this is being called to fetch data
    LaunchedEffect(Unit) {
        viewModel.fetchSunriseSunset()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is SunriseSunsetViewModel.UiState.Loading -> CircularProgressIndicator()
            is SunriseSunsetViewModel.UiState.Success -> {
                val data = (state as SunriseSunsetViewModel.UiState.Success).data
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Sunrise: ${data.sunrise}")
                    Text("Sunset: ${data.sunset}")
                    Text("Solar Noon: ${data.solarNoon}")
                    Text("Day Length: ${data.dayLength}")
                }
            }
            is SunriseSunsetViewModel.UiState.Error -> {
                Text("Error: ${(state as SunriseSunsetViewModel.UiState.Error).message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
