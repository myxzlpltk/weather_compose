package com.myxzlpltk.weather.util.glance

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.myxzlpltk.weather.R
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.usecase.GetCurrentWeatherUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WeatherWidgetReceiver : GlanceAppWidgetReceiver() {

    @Inject
    lateinit var useCase: GetCurrentWeatherUseCase

    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()

    private val coroutineScope = MainScope()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        observeData(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        observeData(context)
    }

    private fun observeData(context: Context) {
        coroutineScope.launch {
            val weather = useCase.getCurrentWeatherNow()
            Timber.d("Weather $weather")
            if (weather == null) return@launch

            GlanceAppWidgetManager(context)
                .getGlanceIds(WeatherWidget::class.java)
                .forEach { glanceId ->
                    updateWidget(context, weather, glanceId)
                }
        }
    }

    private suspend fun updateWidget(context: Context, weather: Weather, glanceId: GlanceId) {
        Timber.d("Update Glance $glanceId")
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) { prefs ->
            prefs.toMutablePreferences().apply {
                this[WEATHER_NAME] = context.getString(weather.weatherStatus.description)
                this[WEATHER_TEMP] = context.getString(
                    R.string.temperature_template,
                    weather.temperature
                )
            }
        }

        glanceAppWidget.update(context, glanceId)
    }

    companion object {
        val WEATHER_NAME = stringPreferencesKey("WEATHER_NAME_GLANCE")
        val WEATHER_TEMP = stringPreferencesKey("WEATHER_TEMP_GLANCE")
    }
}