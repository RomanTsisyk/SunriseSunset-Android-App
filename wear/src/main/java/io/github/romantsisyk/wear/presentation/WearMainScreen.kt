package io.github.romantsisyk.wear.presentation

import androidx.compose.foundation.layout.Column
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
import io.github.romantsisyk.common.core.generateSunData
import io.github.romantsisyk.common.presentation.viewmodel.SunriseSunsetViewModel
import io.github.romantsisyk.common.presentation.viewmodel.UiState

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
                val sunData = generateSunData((uiState as UiState.Success).data)
                sunData.forEach { (icon, label, value) ->
                    item {
                        SunriseSunsetRow(icon = icon, label = label, value = value)
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
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text("$label:")
            Text(value)
        }
    }
}
