package com.serhiiv.trip.misc

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.serhiiv.trip.App
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CurrentLocationProvider @Inject constructor(app: App) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(app.context)
    }

    suspend fun getCurrentPosition(): LatLng = suspendCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                continuation.resume(LatLng(it.latitude, it.longitude))
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}
