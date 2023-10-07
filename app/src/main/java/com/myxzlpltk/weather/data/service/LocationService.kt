package com.myxzlpltk.weather.data.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.myxzlpltk.weather.extension.locationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class LocationService(
    private val context: Context,
    private val fusedLocation: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    fun getLocationFlow(intervalMillis: Long = 1000): Flow<Location> = callbackFlow {
        if (!context.locationPermission()) throw Exception("Missing Permission")

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!hasGPS && hasNetwork) throw Exception("GPS is unavailable")

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            intervalMillis
        ).build()
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.locations.lastOrNull()?.let { location ->
                    launch { send(location) }
                }
            }
        }

        fusedLocation.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocation.removeLocationUpdates(locationCallback)
        }
    }
}