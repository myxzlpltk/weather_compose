package com.myxzlpltk.weather.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.usecase.GetCurrentWeatherUseCase
import com.myxzlpltk.weather.util.worker.FetchDataWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    useCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val workManager = WorkManager.getInstance(context)
    private val workConstraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    private val oneTimeWorkRequest = OneTimeWorkRequestBuilder<FetchDataWorker>()
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .setConstraints(workConstraints)
        .addTag("OneTimeFetchDataWorker")
        .build()

    init {
        initWorker()
    }

    private fun initWorker() {
        workManager.enqueueUniqueWork(
            "OneTimeFetchDataWorker",
            ExistingWorkPolicy.KEEP,
            oneTimeWorkRequest
        )
    }

    private val weatherFlow: Flow<Weather?> = useCase.getCurrentWeather()

    val taskProgress = workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).map { it.state }

    val uiState: StateFlow<HomeUiState> = weatherFlow.map { weather ->
        HomeUiState(
            loading = weather == null,
            weather = weather
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeUiState()
    )
}

data class HomeUiState(
    val loading: Boolean = true,
    val weather: Weather? = null,
)