package com.serhiiv.road.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodedWaypointResponse(
    @field:Json(name = "geocoder_status")
    val geocoderStatus: String? = null,
    @field:Json(name = "place_id")
    val placeId: String? = null,
    @field:Json(name = "types")
    val types: List<String> = emptyList(),
    @field:Json(name = "partial_match")
    val partialMatch: Boolean? = null
)
