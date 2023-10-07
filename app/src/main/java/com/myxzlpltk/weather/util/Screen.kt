package com.myxzlpltk.weather.util

sealed class Screen(val route: String) {
    // Home
    object Home : Screen("Home")
}