package com.myxzlpltk.weather.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myxzlpltk.weather.extension.rotateVertically
import com.myxzlpltk.weather.ui.home.component.WeatherInfoDivider
import com.myxzlpltk.weather.ui.home.component.WeatherInfoPanel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "City Name".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "30°",
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Text(
                modifier = Modifier.rotateVertically(-90f),
                text = "Clear Skies",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(percent = 15)
                )
                .padding(vertical = 16.dp)
        ) {
            WeatherInfoPanel(
                modifier = Modifier.weight(1f),
                title = "Humidity",
                metric = "78%"
            )
            WeatherInfoDivider()
            WeatherInfoPanel(
                modifier = Modifier.weight(1f),
                title = "Visibility",
                metric = "78%"
            )
            WeatherInfoDivider()
            WeatherInfoPanel(
                modifier = Modifier.weight(1f),
                title = "UV Index",
                metric = "Low"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}