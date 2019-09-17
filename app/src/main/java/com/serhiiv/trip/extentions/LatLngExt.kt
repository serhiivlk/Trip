package com.serhiiv.trip.extentions

import com.google.android.gms.maps.model.LatLng

fun LatLng.toCoordinateString(): String = "$latitude,$longitude"
