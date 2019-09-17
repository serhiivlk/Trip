package com.serhiiv.trip.data.remote

import com.serhiiv.trip.data.model.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleDirectionsApiService {
    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): DirectionsResponse

    companion object {
        const val MAPS_ENDPOINT = "https://maps.googleapis.com/maps/api/"
    }
}
