package io.github.romantsisyk.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.romantsisyk.myapplication.domain.usecase.GetSunriseSunsetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import io.github.romantsisyk.myapplication.core.Constants
import io.github.romantsisyk.myapplication.core.Constants.DEFAULT_LATITUDE
import io.github.romantsisyk.myapplication.core.Constants.DEFAULT_LONGITUDE
import io.github.romantsisyk.myapplication.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import javax.inject.Inject

@HiltViewModel
class SunriseSunsetViewModel @Inject constructor(
    private val useCase: GetSunriseSunsetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> get() = _state

    fun fetchSunriseSunset(
        latitude: Double? = null,
        longitude: Double? = null,
        date: String = "today",
        timeZone: TimeZoneID? = null
    ) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            Log.d("ViewModel", "State set to Loading")

            val lat = latitude ?: DEFAULT_LATITUDE
            val lon = longitude ?: DEFAULT_LONGITUDE

            val result = useCase(lat, lon, date, timeZone)
            Log.d( "ViewModel", "result = $result")
            result.fold(
                onSuccess = {
                    Log.d("ViewModel", "State set to Success: $it")
                    _state.value = UiState.Success(it)
                },
                onFailure = {
                    Log.e("ViewModel", "State set to Error: ${it.message}")
                    _state.value = UiState.Error(it.message ?: "Unknown error")
                }
            )
        }
    }


    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: SunriseSunsetTimes) : UiState()
        data class Error(val message: String) : UiState()
    }
}
