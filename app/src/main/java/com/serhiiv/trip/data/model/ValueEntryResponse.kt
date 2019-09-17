package com.serhiiv.road.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValueEntryResponse(
    @field:Json(name = "text")
    val text: String? = null,
    @field:Json(name = "value")
    val value: Int? = null
)
