package com.serhiiv.road.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationResponse(
    @field:Json(name = "lat")
    val lat: Double? = null,
    @field:Json(name = "lng")
    val lng: Double? = null
)
