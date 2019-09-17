package com.serhiiv.trip.data

import android.app.Activity
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CurrentLocationProvider constructor(activity: Activity) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(activity)
    }

    suspend fun getCurrentPosition(): LatLng = suspendCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    continuation.resumeWithException(Exception("Location is null"))
                } else {
                    continuation.resume(LatLng(location.latitude, location.longitude))
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}
