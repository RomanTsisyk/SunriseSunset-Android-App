package io.github.romantsisyk.common.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.romantsisyk.common.domain.usecase.GetSunriseSunsetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import io.github.romantsisyk.common.core.Constants.DEFAULT_LATITUDE
import io.github.romantsisyk.common.core.Constants.DEFAULT_LONGITUDE
import io.github.romantsisyk.common.domain.model.City
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import javax.inject.Inject

@HiltViewModel
class SunriseSunsetViewModel @Inject constructor(
    private val useCase: GetSunriseSunsetUseCase
) : ViewModel() {
    
    val cities = listOf(
        City("Ternopil", 49.5535, 25.5948, TimeZoneID.EUROPE_KIEV),
        City("Wroclaw", 51.1079, 17.0385, TimeZoneID.EUROPE_WARSAW),
        City("Barcelona", 41.3874, 2.1686, TimeZoneID.EUROPE_MADRID),
        City("Chicago", 41.8781, -87.6298, TimeZoneID.AMERICA_CHICAGO)
    )

    private val _selectedCity = MutableStateFlow(cities.first())
    val selectedCity: StateFlow<City> = _selectedCity

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchSunriseSunsetData()
    }

    fun updateSelectedCity(city: City) {
        _selectedCity.value = city
        fetchSunriseSunsetData()
    }

    private fun fetchSunriseSunsetData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val city = _selectedCity.value
            val data = useCase(
                city.latitude ?: DEFAULT_LATITUDE,
                city.longitude ?: DEFAULT_LONGITUDE,
                "today",
                city.timeZone
            )
            data.fold(
                onSuccess = {
                    Log.d("ViewModel", "State set to Success: $it")
                    _uiState.value = UiState.Success(it)
                },
                onFailure = {
                    Log.e("ViewModel", "State set to Error: ${it.message}")
                    _uiState.value = UiState.Error(it.message ?: "Unknown error")
                }
            )
        }
    }
}
