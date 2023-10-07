package com.myxzlpltk.weather.ui.home.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myxzlpltk.weather.ui.base.VerticalDivider

@Composable
fun WeatherInfoDivider(
    modifier: Modifier = Modifier
) {
    VerticalDivider(
        modifier = modifier,
        color = Color.Red,
        thickness = 1.dp
    )
}

@Preview
@Composable
private fun PreviewWeatherInfoDivider() {
    WeatherInfoDivider()
}