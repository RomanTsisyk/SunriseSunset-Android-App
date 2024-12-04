package io.github.romantsisyk.myapplication.domain.usecase

import android.util.Log
import io.github.romantsisyk.myapplication.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.myapplication.domain.repository.SunriseSunsetRepositoryInterface
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID

class GetSunriseSunsetUseCase(
    private val repository: SunriseSunsetRepositoryInterface
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        date: String,
        timeZone: TimeZoneID?
    ): Result<SunriseSunsetTimes> {
        val result = repository.fetchSunriseSunsetTimes(latitude= 36.7201600, longitude = -4.4203400, date ="today", timeZone = TimeZoneID.EUROPE_LONDON)
        Log.d("GetSunriseSunsetUseCase" , "result = $result" )
        return result
    }
}
