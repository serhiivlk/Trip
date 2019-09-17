package com.serhiiv.trip.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LegResponse(
    @field:Json(name = "distance")
    val distance: ValueEntryResponse? = null,
    @field:Json(name = "duration")
    val duration: ValueEntryResponse? = null,
    @field:Json(name = "end_address")
    val endAddress: String? = null,
    @field:Json(name = "end_location")
    val endLocation: LocationResponse? = null,
    @field:Json(name = "start_address")
    val startAddress: String? = null,
    @field:Json(name = "start_location")
    val startLocation: LocationResponse? = null
)
