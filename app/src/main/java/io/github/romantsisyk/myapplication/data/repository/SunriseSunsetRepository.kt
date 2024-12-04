package io.github.romantsisyk.myapplication.data.repository

import android.util.Log
import io.github.romantsisyk.myapplication.domain.repository.SunriseSunsetRepositoryInterface
import io.github.romantsisyk.sunrisesunsetsdk.SunriseSunsetSDK
import io.github.romantsisyk.sunrisesunsetsdk.models.SuccessApiResponse
import io.github.romantsisyk.myapplication.domain.model.SunriseSunsetTimes
import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SunriseSunsetRepository @Inject constructor(
    private val sdk: SunriseSunsetSDK
) : SunriseSunsetRepositoryInterface {

    override suspend fun fetchSunriseSunsetTimes(
        latitude: Double,
        longitude: Double,
        date: String,
        timeZone: TimeZoneID?
    ): Result<SunriseSunsetTimes> {
        Log.d("SunriseSunsetRepository", "fetchSunriseSunsetTimes: Start fetching data")
        Log.d("SunriseSunsetRepository", "Parameters: lat=$latitude, lng=$longitude, date=$date, timeZone=$timeZone")

        return withContext(Dispatchers.IO) {
            try {
                val response = sdk.getSunTimesAsync(
                    lat = latitude,
                    lng = longitude,
                    date = date,
                    formatted = 0,
                    tzid = timeZone
                )
                Log.d("SunriseSunsetRepository", "Response received: status=${response.status}")

                if (response.status == "OK") {
                    response as SuccessApiResponse
                    val result = SunriseSunsetTimes(
                        sunrise = response.results.sunrise,
                        sunset = response.results.sunset,
                        solarNoon = response.results.solarNoon,
                        dayLength = response.results.dayLength
                    )
                    Log.d("SunriseSunsetRepository", "Parsed Result: $result")
                    Result.success(result)
                } else {
                    Log.e("SunriseSunsetRepository", "API Error: ${response.status}")
                    Result.failure(Exception("API Error: ${response.status}"))
                }
            } catch (e: Exception) {
                Log.e("SunriseSunsetRepository", "Error fetching data: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}