package com.myxzlpltk.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.myxzlpltk.weather.ui.theme.WeatherTheme
import com.myxzlpltk.weather.util.toEpochMilli
import com.myxzlpltk.weather.util.worker.FetchDataWorker
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This will lay out our app behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WeatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp()
                }
            }
        }

        // Initiate work manager
        initWorker()
    }

    private fun initWorker() {
        val now = LocalDateTime.now()
        val currentHour = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1)
        val delay = currentHour.toEpochMilli() - now.toEpochMilli() + 5000

        val workManager = WorkManager.getInstance(this)
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // Create work request
        val periodicWorkRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(1, TimeUnit.HOURS)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(1))
            .setInitialDelay(Duration.ofMillis(delay))
            .setConstraints(workConstraints)
            .addTag("PeriodicFetchDataWorker")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "PeriodicFetchDataWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }
}