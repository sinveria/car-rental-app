package ru.sinveria.rentcar.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object GettingStarted : Screen("getting_started_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object SignUpOne : Screen("sign_up_one_screen")
    object SignUpTwo : Screen("sign_up_two_screen")
    object SignUpThree : Screen("sign_up_three_screen")
    object Сongratulations : Screen("congratulations_screen")
}