package io.github.romantsisyk.wear.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import io.github.romantsisyk.common.presentation.viewmodel.SunriseSunsetViewModel
import io.github.romantsisyk.common.presentation.viewmodel.UiState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WearMainScreen(viewModel: SunriseSunsetViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is UiState.Loading -> item {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val data = (uiState as UiState.Success).data

                val sunData = listOf(
                    "☀️" to "Sunrise" to data.sunrise,
                    "🌙" to "Sunset" to data.sunset,
                    "🌞" to "Solar Noon" to data.solarNoon,
                    "⏳" to "Day Length" to data.dayLength,
                    "🌅" to "Civil Twilight Begin" to data.civilTwilightBegin,
                    "🌆" to "Civil Twilight End" to data.civilTwilightEnd,
                    "🌌" to "Nautical Twilight Begin" to data.nauticalTwilightBegin,
                    "🌉" to "Nautical Twilight End" to data.nauticalTwilightEnd,
                    "🌠" to "Astronomical Twilight Begin" to data.astronomicalTwilightBegin,
                    "🌌" to "Astronomical Twilight End" to data.astronomicalTwilightEnd
                )

                sunData.forEach { (iconLabel, value) ->
                    item {
                        SunriseSunsetRow(
                            icon = iconLabel.first,
                            label = iconLabel.second,
                            value = value
                        )
                    }
                }
            }

            is UiState.Error -> item {
                Text("Error: ${(uiState as UiState.Error).message}")
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
        Text(icon)
        Spacer(modifier = Modifier.width(8.dp))
        Text("$label: ${value.formatDate()}")
    }
}


fun String.formatDate(): String {
    return try {
        val parsedDate = ZonedDateTime.parse(this)
        parsedDate.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
    } catch (e: Exception) {
        this
    }
}