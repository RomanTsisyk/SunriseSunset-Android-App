package io.github.romantsisyk.myapplication.presentation

import io.github.romantsisyk.myapplication.domain.model.SunriseSunsetTimes


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: SunriseSunsetTimes) : UiState()
    data class Error(val message: String) : UiState()
}