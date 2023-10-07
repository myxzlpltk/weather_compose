package com.myxzlpltk.weather.util.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.myxzlpltk.weather.MainActivity
import com.myxzlpltk.weather.R

class WeatherWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val weatherName = prefs[WeatherWidgetReceiver.WEATHER_NAME]
            val weatherTemp = prefs[WeatherWidgetReceiver.WEATHER_TEMP]

            if (weatherName != null && weatherTemp != null) {
                MyContent(
                    modifier = GlanceModifier.clickable(actionStartActivity<MainActivity>()),
                    weatherName = weatherName,
                    weatherTemp = weatherTemp
                )
            } else {
                Box(
                    modifier = GlanceModifier
                        .appWidgetBackground()
                        .background(Color.White)
                        .fillMaxSize()
                        .clickable(actionStartActivity<MainActivity>()),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    @Composable
    private fun MyContent(
        modifier: GlanceModifier = GlanceModifier,
        weatherName: String,
        weatherTemp: String,
    ) {
        val context = LocalContext.current

        Box(
            modifier = modifier
                .appWidgetBackground()
                .fillMaxSize()
        ) {
            Image(
                modifier = GlanceModifier.fillMaxSize(),
                provider = ImageProvider(R.drawable.clear_sky),
                contentDescription = context.getString(R.string.background_weather),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    ColorProvider(Color.Black.copy(alpha = 0.4f))
                )
            )

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weatherName,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = ColorProvider(Color.White)
                    ),
                )
                Text(
                    text = weatherTemp,
                    style = TextStyle(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorProvider(Color.White)
                    )
                )
            }
        }
    }
}