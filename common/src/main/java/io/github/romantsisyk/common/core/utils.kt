package io.github.romantsisyk.common.core

import io.github.romantsisyk.common.domain.model.SunriseSunsetTimes
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun generateSunData(data: SunriseSunsetTimes): List<Triple<String, String, String>> {
    return listOf(
        Triple("☀️", "Sunrise", data.sunrise.formatDate()),
        Triple("🌙", "Sunset", data.sunset.formatDate()),
        Triple("🌞", "Solar Noon", data.solarNoon.formatDate()),
        Triple("⏳", "Day Length", data.dayLength.formatDate()),
        Triple("🌅", "Civil Twilight Begin", data.civilTwilightBegin.formatDate()),
        Triple("🌆", "Civil Twilight End", data.civilTwilightEnd.formatDate()),
        Triple("🌌", "Nautical Twilight Begin", data.nauticalTwilightBegin.formatDate()),
        Triple("🌉", "Nautical Twilight End", data.nauticalTwilightEnd.formatDate()),
        Triple("🌠", "Astronomical Twilight Begin", data.astronomicalTwilightBegin.formatDate()),
        Triple("🌌", "Astronomical Twilight End", data.astronomicalTwilightEnd.formatDate())
    )
}


fun String.formatDate(): String {
    return try {
        val parsedDate = ZonedDateTime.parse(this)
        parsedDate.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))
    } catch (e: Exception) {
        this // Fallback to raw string if parsing fails
    }
}

