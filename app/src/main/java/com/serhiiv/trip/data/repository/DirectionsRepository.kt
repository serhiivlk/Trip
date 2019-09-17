package com.serhiiv.trip.data.repository

import com.google.android.gms.maps.model.LatLng
import com.serhiiv.trip.App
import com.serhiiv.trip.R
import com.serhiiv.trip.data.model.DirectionsResponse
import com.serhiiv.trip.data.remote.GoogleDirectionsApiService
import com.serhiiv.trip.extentions.toCoordinateString
import javax.inject.Inject

class DirectionsRepository @Inject constructor(
    app: App,
    private val apiService: GoogleDirectionsApiService
) {
    private val apiKey = app.context.getString(R.string.google_maps_key)

    suspend fun getDestination(origin: LatLng, destination: LatLng): DirectionsResponse {
        return apiService.getDirections(
            origin = origin.toCoordinateString(),
            destination = destination.toCoordinateString(),
            key = apiKey
        )
    }
}
