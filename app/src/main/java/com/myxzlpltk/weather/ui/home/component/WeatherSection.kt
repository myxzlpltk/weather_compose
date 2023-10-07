package com.myxzlpltk.weather.ui.home.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myxzlpltk.weather.R
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.extension.dummyWeather
import com.myxzlpltk.weather.extension.rotateVertically

@Composable
fun WeatherSection(
    modifier: Modifier = Modifier,
    weather: Weather,
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
                    text = weather.cityName.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.temperature_template, weather.temperature),
                    style = MaterialTheme.typography.displayLarge,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                modifier = Modifier.rotateVertically(-90f),
                text = stringResource(weather.weatherStatus.description),
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
                title = stringResource(R.string.humidity),
                metric = stringResource(R.string.humidity_template, weather.humidity)
            )
            WeatherInfoDivider()
            WeatherInfoPanel(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.wind_speed),
                metric = stringResource(R.string.wind_speed_template, weather.windSpeed)
            )
            WeatherInfoDivider()
            WeatherInfoPanel(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.precipitation),
                metric = stringResource(
                    R.string.precipitation_template,
                    weather.precipitationProbability
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherSection() {
    WeatherSection(
        weather = dummyWeather()
    )
}