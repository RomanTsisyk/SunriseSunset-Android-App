package io.github.romantsisyk.common.presentation.viewmodel

import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: SunriseSunsetTimes) : UiState()
    data class Error(val message: String) : UiState()
}