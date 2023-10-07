package com.myxzlpltk.weather.util.worker

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import com.myxzlpltk.weather.domain.repository.WeatherRepository
import com.myxzlpltk.weather.extension.locationPermission
import com.myxzlpltk.weather.util.glance.WeatherWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltWorker
class FetchDataWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository
) : CoroutineWorker(appContext, workerParams) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val location = getLocation()
            if (location == null) {
                if (runAttemptCount < MAX_ATTEMPT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            } else {
                // Get location
                val cityName = getCityName(location)

                // Update repository
                val weathers = weatherRepository.fetchForecast(
                    location.latitude,
                    location.longitude,
                    cityName
                )

                // Update glance
                updateGlance()

                Timber.tag(TAG).d("doWork success $location")

                val outputData = Data.Builder()
                    .putString("cityName", cityName)
                    .putString("location", "${location.latitude}, ${location.longitude}")
                    .putString("totalWeathers", weathers.size.toString())
                    .build()

                Result.success(outputData)
            }
        } catch (e: Exception) {
            val outputData = Data.Builder()
                .putString("message", e.message)
                .build()

            Result.failure(outputData)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location? = suspendCancellableCoroutine { continuation ->
        if (!appContext.locationPermission()) throw Exception("Permission missing")

        val tokenSource = CancellationTokenSource()
        fusedLocationClient.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, tokenSource.token)
            .addOnSuccessListener { continuation.resume(it) }
            .addOnFailureListener { continuation.resumeWithException(it) }

        continuation.invokeOnCancellation {
            tokenSource.cancel()
        }
    }

    private suspend fun getCityName(location: Location): String = suspendCoroutine { continuation ->
        val geocoder = Geocoder(appContext, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) { addresses ->
                if (addresses.isNotEmpty()) {
                    continuation.resume(addresses.first().locality)
                } else {
                    continuation.resumeWithException(Exception("Empty Address"))
                }
            }
        } else {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            if (!addresses.isNullOrEmpty()) {
                continuation.resume(addresses.first().locality)
            } else {
                continuation.resumeWithException(Exception("Empty Address"))
            }
        }
    }

    private suspend fun updateGlance() {
        WeatherWidget().updateAll(appContext)
    }

    companion object {
        const val TAG = "FetchDataWorker"
        const val MAX_ATTEMPT = 3
    }
}