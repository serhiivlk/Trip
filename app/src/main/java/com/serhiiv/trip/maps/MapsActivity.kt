package com.serhiiv.trip.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.serhiiv.trip.R
import com.serhiiv.trip.utils.Markers
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MapsActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var factory: MapsViewModel.Factory

    private val viewModel: MapsViewModel by viewModels { factory }

    private lateinit var map: GoogleMap

    private lateinit var originMarker: Marker
    private lateinit var destinationMarker: Marker
    private lateinit var routePolyline: Polyline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapLongClickListener { viewModel.processDirection(it) }

        enableMyLocationIfPermitted()

        viewModel.state.observe(this) { state ->
            map.isMyLocationEnabled = state.currentLocationIsEnable

            state.originPosition?.let(::updateOriginMarkerPosition)
            state.destinationPosition?.let {
                updateDestinationMarkerPosition(it, state.tripInfo)
            }
            state.polylinePoints.let(::updatePolyline)
            state.infoMessage?.consume()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

            state.error?.consume()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RC_LOCATION_PERMISSION -> {
                enableMyLocationIfPermitted()
            }
        }
    }

    private fun updateOriginMarkerPosition(position: LatLng) {
        if (::originMarker.isInitialized.not()) {
            originMarker = map.addMarker(Markers.buildOriginOptions(position))
            map.moveCamera(CameraUpdateFactory.newLatLng(originMarker.position))
        } else {
            originMarker.position = position
        }
    }

    private fun updateDestinationMarkerPosition(position: LatLng, title: String?) {
        if (::destinationMarker.isInitialized.not()) {
            destinationMarker = map.addMarker(Markers.buildDestinationOptions(position))
        } else {
            destinationMarker.position = position
        }
        title?.let {
            destinationMarker.title = it
            destinationMarker.showInfoWindow()
        }
    }

    private fun updatePolyline(points: String?) {
        val positions = points?.let(PolyUtil::decode) ?: emptyList()
        if (::routePolyline.isInitialized.not()) {
            routePolyline = map.addPolyline(PolylineOptions().addAll(positions))
        } else {
            routePolyline.points = positions
        }
    }

    private fun enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_LONG).show()
                viewModel.setupCurrentLocation()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    RC_LOCATION_PERMISSION
                )
            }
        } else {
            viewModel.setupCurrentLocation()
        }
    }

    companion object {
        private const val RC_LOCATION_PERMISSION = 0x50
    }
}
