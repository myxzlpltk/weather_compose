package com.myxzlpltk.weather.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.myxzlpltk.weather.R
import com.myxzlpltk.weather.ui.home.component.WeatherSection

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val weather = uiState.weather

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        if (weather != null) {
            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = false
                )
            }

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(weather.weatherStatus.image),
                contentDescription = stringResource(R.string.background_weather),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    Color.Black.copy(alpha = 0.25f),
                    BlendMode.Darken
                )
            )

            WeatherSection(
                modifier = Modifier.systemBarsPadding(),
                weather = weather
            )
        } else {
            val background = MaterialTheme.colorScheme.background

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = background,
                    darkIcons = useDarkIcons
                )
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}