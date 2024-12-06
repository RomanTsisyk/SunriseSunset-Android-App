package io.github.romantsisyk.common.domain.model

import io.github.romantsisyk.sunrisesunsetsdk.utils.TimeZoneID

data class City(
    val name: String,
    val latitude: Double ?,
    val longitude: Double ?,
    val timeZone: TimeZoneID
)