package ru.sinveria.rentcar.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object NoConnection : Screen("no_connection_screen")
    object GettingStarted : Screen("getting_started_screen")
    object Onboarding : Screen("onboarding_screen")
    object Login : Screen("login_screen")
    object SignUpOne : Screen("sign_up_one_screen")
    object SignUpTwo : Screen("sign_up_two_screen")
    object SignUpThree : Screen("sign_up_three_screen")
    object Сongratulations : Screen("congratulations_screen")
    object UserProfile : Screen("user_profile_screen")
    object SettingsScreen : Screen(route = "settings_user")
}