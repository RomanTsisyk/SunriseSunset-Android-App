package io.github.romantsisyk.myapplication.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.common.domain.model.City
import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.common.presentation.viewmodel.UiState
import io.github.romantsisyk.common.presentation.viewmodel.SunriseSunsetViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
//        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SunriseSunsetRow("☀️", "Sunrise", data.sunrise.formatDate())
            SunriseSunsetRow("🌙", "Sunset", data.sunset.formatDate())
            SunriseSunsetRow("🌞", "Solar Noon", data.solarNoon.formatDate())
            SunriseSunsetRow("⏳", "Day Length", data.dayLength)
            SunriseSunsetRow("🌅", "Civil Twilight Begin", data.civilTwilightBegin.formatDate())
            SunriseSunsetRow("🌆", "Civil Twilight End", data.civilTwilightEnd.formatDate())
            SunriseSunsetRow("🌌", "Nautical Twilight Begin", data.nauticalTwilightBegin.formatDate())
            SunriseSunsetRow("🌉", "Nautical Twilight End", data.nauticalTwilightEnd.formatDate())
            SunriseSunsetRow("🌠", "Astronomical Twilight Begin", data.astronomicalTwilightBegin.formatDate())
            SunriseSunsetRow("🌌", "Astronomical Twilight End", data.astronomicalTwilightEnd.formatDate())
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

@ExperimentalMaterial3Api
@Composable
fun AppHeader() {
    TopAppBar(
        title = { Text("Sunrise-Sunset App") },
//        backgroundColor = MaterialTheme.colorScheme.primary,
//        contentColor = Color.White,
//        elevation = 4.dp
    )
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


fun String.formatDate(): String {
    return try {
        val parsedDate = ZonedDateTime.parse(this)
        parsedDate.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
    } catch (e: Exception) {
        this // Fallback to raw string if parsing fails
    }
}

