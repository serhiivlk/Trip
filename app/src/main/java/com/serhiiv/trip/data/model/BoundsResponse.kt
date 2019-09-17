package com.serhiiv.road.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoundsResponse(
    @field:Json(name = "northeast")
    val northeast: LocationResponse? = null,
    @field:Json(name = "southwest")
    val southwest: LocationResponse? = null
)
