package ru.sinveria.rentcar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.sinveria.rentcar.ui.screens.GettingStarted
import ru.sinveria.rentcar.ui.screens.Login
import ru.sinveria.rentcar.ui.screens.Onboarding
import ru.sinveria.rentcar.ui.screens.SignUpOne
import ru.sinveria.rentcar.ui.screens.SignUpThree
import ru.sinveria.rentcar.ui.screens.SignUpTwo
import ru.sinveria.rentcar.ui.screens.SplashScreen
import ru.sinveria.rentcar.ui.screens.Congratulations

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
                },

                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },

                onNavigateToSignUpOne = {
                    navController.navigate(Screen.SignUpOne.route)
                }
            )
        }
        composable(Screen.Onboarding.route) {
            Onboarding(
                onNavigateToGettingStarted = {
                    navController.navigate(Screen.GettingStarted.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            Login(
                onNavigateToSignUpOne = {
                    navController.navigate(Screen.SignUpOne.route)
                }
            )
        }

        composable(Screen.SignUpOne.route) {
            SignUpOne(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSignUpTwo = {
                    navController.navigate(Screen.SignUpTwo.route)
                }
            )
        }
        composable(Screen.SignUpTwo.route) {
            SignUpTwo(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSignUpThree = {
                    navController.navigate(Screen.SignUpThree.route)
                }
            )
        }

        composable(Screen.SignUpThree.route) {
            SignUpThree(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCong = {
                    navController.navigate(Screen.Сongratulations.route)
                }
            )
        }

        composable(Screen.Сongratulations.route) {
            Congratulations()
        }
    }
}
