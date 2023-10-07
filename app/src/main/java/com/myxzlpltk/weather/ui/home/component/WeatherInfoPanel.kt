package com.myxzlpltk.weather.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherInfoPanel(
    modifier: Modifier = Modifier,
    title: String,
    metric: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = metric,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewWeatherInfoPanel() {
    WeatherInfoPanel(
        title = "Humidity",
        metric = "78%"
    )
}