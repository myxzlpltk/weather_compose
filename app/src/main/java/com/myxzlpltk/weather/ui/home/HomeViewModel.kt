package com.myxzlpltk.weather.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.usecase.GetCurrentWeatherUseCase
import com.myxzlpltk.weather.worker.FetchDataWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val workManager = WorkManager.getInstance(context)

    private fun initWorker() {
        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create work request
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<FetchDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(workConstraints)
            .addTag("OneTimeFetchDataWorker")
            .build()

        workManager.enqueueUniqueWork(
            "OneTimeFetchDataWorker",
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest
        )
    }

    private val weatherFlow: Flow<Weather?> = useCase.getCurrentWeather().onEach { weather ->
        if (weather == null) {
            initWorker()
        }
    }

    val uiState: StateFlow<HomeUiState> = weatherFlow.map { weather ->
        if (weather == null) {
            HomeUiState.Loading
        } else {
            HomeUiState.Success(weather)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeUiState.Loading
    )
}

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val weather: Weather
    ) : HomeUiState
}