package com.serhiiv.trip.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DirectionsResponse(
    @field:Json(name = "geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypointResponse> = emptyList(),
    @field:Json(name = "routes")
    val routes: List<RouteResponse> = emptyList(),
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "error_message")
    val errorMessage: String? = null
)
