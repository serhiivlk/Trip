package com.serhiiv.trip.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RouteResponse(
    @field:Json(name = "bounds")
    val bounds: BoundsResponse? = null,
    @field:Json(name = "copyrights")
    val copyrights: String? = null,
    @field:Json(name = "legs")
    val legs: List<LegResponse> = emptyList(),
    @field:Json(name = "overview_polyline")
    val overviewPolyline: OverviewPolylineResponse? = null,
    @field:Json(name = "summary")
    val summary: String? = null,
    @field:Json(name = "warnings")
    val warnings: List<Any> = emptyList(),
    @field:Json(name = "waypoint_order")
    val waypointOrder: List<Any> = emptyList()
)
