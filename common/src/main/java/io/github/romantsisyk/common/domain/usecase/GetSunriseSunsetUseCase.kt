package io.github.romantsisyk.common.domain.usecase

import android.util.Log
import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.common.domain.repository.SunriseSunsetRepositoryInterface
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
        val result = repository.fetchSunriseSunsetTimes(
            latitude = latitude,
            longitude = longitude,
            date = "today",
            timeZone = timeZone
        )
        Log.d("GetSunriseSunsetUseCase", "result = $result")
        return result
    }
}
