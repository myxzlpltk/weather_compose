package com.myxzlpltk.weather.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

fun countDownFlow(timeInMillis: Long = 1000L) = flow {
    while (true) {
        emit(LocalDateTime.now())
        delay(timeInMillis)
    }
}