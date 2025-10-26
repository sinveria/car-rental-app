package ru.sinveria.rentcar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.sinveria.rentcar.presentation.ui.screens.GettingStarted
import ru.sinveria.rentcar.presentation.ui.screens.Login
import ru.sinveria.rentcar.presentation.ui.screens.Onboarding
import ru.sinveria.rentcar.presentation.ui.screens.SignUpOne
import ru.sinveria.rentcar.presentation.ui.screens.SignUpThree
import ru.sinveria.rentcar.presentation.ui.screens.SignUpTwo
import ru.sinveria.rentcar.presentation.ui.screens.SplashScreen
import ru.sinveria.rentcar.presentation.ui.screens.Congratulations
import ru.sinveria.rentcar.presentation.ui.screens.NoConnection
import ru.sinveria.rentcar.presentation.ui.screens.UserProfileScreen
import ru.sinveria.rentcar.presentation.viewmodel.RegistrationSharedViewModel

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
                },
                onNoConnection = {
                    navController.navigate(Screen.NoConnection.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.NoConnection.route) {
            NoConnection(
                onConnectionRestored = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.NoConnection.route) { inclusive = true }
                    }
                },
                onRetryClick = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(Screen.NoConnection.route) { inclusive = true }
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
                    navController.navigate("registration_graph") {
                        launchSingleTop = true
                    }
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
                    navController.navigate("registration_graph") {
                        launchSingleTop = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate(Screen.UserProfile.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        navigation(
            startDestination = Screen.SignUpOne.route,
            route = "registration_graph"
        ) {
            composable(Screen.SignUpOne.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("registration_graph")
                }
                val sharedViewModel: RegistrationSharedViewModel = hiltViewModel(parentEntry)

                SignUpOne(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToSignUpTwo = {
                        navController.navigate(Screen.SignUpTwo.route)
                    },
                    sharedViewModel = sharedViewModel
                )
            }

            composable(Screen.SignUpTwo.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("registration_graph")
                }
                val sharedViewModel: RegistrationSharedViewModel = hiltViewModel(parentEntry)

                SignUpTwo(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToSignUpThree = {
                        navController.navigate(Screen.SignUpThree.route)
                    },
                    sharedViewModel = sharedViewModel
                )
            }

            composable(Screen.SignUpThree.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("registration_graph")
                }
                val sharedViewModel: RegistrationSharedViewModel = hiltViewModel(parentEntry)

                SignUpThree(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToCong = {
                        navController.navigate(Screen.Сongratulations.route)
                    },
                    sharedViewModel = sharedViewModel
                )
            }

            composable(Screen.Сongratulations.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("registration_graph")
                }
                val sharedViewModel: RegistrationSharedViewModel = hiltViewModel(parentEntry)

                Congratulations(
                    onNavigateToProfile = {
                        navController.navigate(Screen.UserProfile.route)
                    },
                    sharedViewModel = sharedViewModel
                )
            }

            composable(Screen.UserProfile.route) {
                UserProfileScreen(
                    onNavigateBack = {
                        navController.navigate(Screen.GettingStarted.route) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    }
}