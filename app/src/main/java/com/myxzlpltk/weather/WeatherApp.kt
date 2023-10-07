package com.myxzlpltk.weather

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.myxzlpltk.weather.ui.AppRouter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier
) {
    val locationPermissionState = rememberPermissionState(ACCESS_COARSE_LOCATION)

    Scaffold(
        modifier = modifier
    ) { contentPadding ->
        if (locationPermissionState.status.isGranted) {
            AppRouter(
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            LaunchedEffect(Unit) {
                locationPermissionState.launchPermissionRequest()
            }

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.location_request_message),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text(text = stringResource(R.string.request_permission))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherApp() {
    WeatherApp()
}