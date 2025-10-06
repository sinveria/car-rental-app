package ru.sinveria.rentcar.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object GettingStarted : Screen("getting_started_screen")
    object Onboarding : Screen("onboarding_screen")
    // другие экраны
}