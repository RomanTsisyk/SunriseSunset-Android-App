package io.github.romantsisyk.myapplication.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.common.core.generateSunData
import io.github.romantsisyk.common.domain.model.City
import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.common.presentation.viewmodel.SunriseSunsetViewModel
import io.github.romantsisyk.common.presentation.viewmodel.UiState

@Composable
fun MainScreen(viewModel: SunriseSunsetViewModel) {
    BackgroundGradient {

        val selectedCity by viewModel.selectedCity.collectAsState()
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Dropdown for city selection
            DropdownMenu(
                cities = viewModel.cities,
                selectedCity = selectedCity,
                onCitySelected = { viewModel.updateSelectedCity(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Handle UI state
            when (uiState) {
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Success -> {
                    val data = (uiState as UiState.Success).data
                    DisplaySunTimes(data)
                }

                is UiState.Error -> {
                    Text(
                        text = (uiState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Footer()
        }
    }
}

@Composable
fun DropdownMenu(
    cities: List<City>,
    selectedCity: City,
    onCitySelected: (City) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Text(
                text = "Selected City: ${selectedCity.name}",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city.name, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        onCitySelected(city)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DisplaySunTimes(data: SunriseSunsetTimes) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            generateSunData(data).forEach { (icon, label, value) ->
                SunriseSunsetRow(icon = icon, label = label, value = value)
            }
        }
    }
}


@Composable
fun SunriseSunsetRow(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(8.dp))
        Text("$label: $value", style = MaterialTheme.typography.bodyLarge)
    }
}


@Composable
fun BackgroundGradient(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD), // Light blue
                        Color(0xFFBBDEFB)  // Slightly darker blue
                    )
                )
            )
    ) {
        content()
    }
}


@Composable
fun Footer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "This is a test app to showcase and test my Sunrise-Sunset SDK.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}