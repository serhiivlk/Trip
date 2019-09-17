package com.serhiiv.trip.utils

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

object Markers {
    fun buildOriginOptions(position: LatLng) =
        MarkerOptions()
            .position(position)
            .title("Origin")
            .icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )

    fun buildDestinationOptions(position: LatLng) =
        MarkerOptions()
            .position(position)
            .title("Destination")
            .icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
            )
}
