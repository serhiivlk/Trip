package com.serhiiv.trip.data.remote

import com.serhiiv.road.data.model.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleDirectionApiService {
    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): DirectionsResponse
}
