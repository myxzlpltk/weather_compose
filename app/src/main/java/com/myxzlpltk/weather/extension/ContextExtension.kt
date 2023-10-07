package com.myxzlpltk.weather.extension

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.locationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    // this recursion should be okay since we call getActivity on a view context
    // that should have an Activity as its baseContext at some point
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}