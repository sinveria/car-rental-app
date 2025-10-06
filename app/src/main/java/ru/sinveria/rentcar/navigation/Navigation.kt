package ru.sinveria.rentcar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sinveria.rentcar.ui.screens.GettingStarted
import ru.sinveria.rentcar.ui.screens.Onboarding
import ru.sinveria.rentcar.ui.screens.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.GettingStarted.route) {
            GettingStarted(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route)
                }
            )
        }
        composable(Screen.Onboarding.route) {
            Onboarding(
                onNavigateToGettingStarted = {
                    //  переход на главный экран
                    navController.navigate(Screen.GettingStarted.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
    }
}