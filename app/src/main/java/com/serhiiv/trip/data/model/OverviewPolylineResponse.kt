package com.serhiiv.trip.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OverviewPolylineResponse(
    @field:Json(name = "points")
    val points: String? = null
)
