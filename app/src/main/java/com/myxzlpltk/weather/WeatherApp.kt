package com.myxzlpltk.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myxzlpltk.weather.ui.AppRouter

@Composable
fun WeatherApp(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { contentPadding ->
        AppRouter(
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Preview
@Composable
private fun PreviewWeatherApp() {
    WeatherApp()
}