package ru.sinveria.rentcar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.coroutines.delay
import ru.sinveria.rentcar.presentation.ui.screens.AddCarOne
import ru.sinveria.rentcar.presentation.ui.screens.AddCarPhotos
import ru.sinveria.rentcar.presentation.ui.screens.AddCarSuccess
import ru.sinveria.rentcar.presentation.ui.screens.AddCarTwo
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
import ru.sinveria.rentcar.presentation.ui.screens.HomeScreen
import ru.sinveria.rentcar.presentation.ui.screens.SettingsScreen
import ru.sinveria.rentcar.presentation.ui.screens.SearchLoadingScreen
import ru.sinveria.rentcar.presentation.ui.screens.SearchResultsScreen
import ru.sinveria.rentcar.presentation.viewmodel.RegistrationSharedViewModel
import ru.sinveria.rentcar.presentation.viewmodel.SearchViewModel
import androidx.compose.runtime.getValue

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
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCarDetailsClick = {

                },
                onBookCarClick = {

                },
                onBookmarksClick = {

                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onSearchLoading = { searchQuery ->
                    navController.navigate("search_loading/$searchQuery")
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onBookingsClick = {
                },
                onThemeClick = {
                },
                onNotificationsClick = {
                },
                onConnectCarClick = {
                    navController.navigate("add_car_graph")
                },
                onHelpClick = {
                },
                onInviteFriendClick = {
                },
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0)
                    }
                },
                onProfileClick = {
                    navController.navigate(Screen.UserProfile.route)
                }
            )
        }

        composable("search_loading/{searchQuery}") { backStackEntry ->
            val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""

            SearchLoadingScreen()

            LaunchedEffect(Unit) {
                delay(2000)
                navController.navigate("search_results/$searchQuery") {
                    popUpTo(Screen.Home.route)
                }
            }
        }

        composable("search_results/{searchQuery}") { backStackEntry ->
            val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchResults by searchViewModel.searchResults.collectAsState()
            val isLoading by searchViewModel.isLoading.collectAsState()

            LaunchedEffect(searchQuery) {
                searchViewModel.searchCars(searchQuery)
            }

            SearchResultsScreen(
                searchQuery = searchQuery,
                searchResults = searchResults,
                isLoading = isLoading,
                onBackClick = {
                    navController.popBackStack()
                },
                onCarDetailsClick = { carId ->
                },
                onBookCarClick = { carId ->
                },
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0)
                    }
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onBookmarksClick = {
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
                        navController.navigate(Screen.Home.route) {
                            popUpTo(0)
                        }
                    },
                    sharedViewModel = sharedViewModel
                )
            }
        }

        composable(Screen.UserProfile.route) {
            UserProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                },
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0)
                    }
                },
                onBookmarksClick = {
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onAddCarClick = {
                    navController.navigate("add_car_graph")
                }
            )
        }

        navigation(
            startDestination = Screen.AddCarOne.route,
            route = "add_car_graph"
        ) {
            composable(Screen.AddCarOne.route) {
                AddCarOne(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNext = {
                        navController.navigate(Screen.AddCarTwo.route)
                    }
                )
            }

            composable(Screen.AddCarTwo.route) {
                AddCarTwo(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onSubmit = {
                        navController.navigate(Screen.AddCarPhotos.route)
                    }
                )
            }

            composable(Screen.AddCarPhotos.route) {
                AddCarPhotos(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNext = {
                        navController.navigate(Screen.AddCarSuccess.route)
                    }
                )
            }

            composable(Screen.AddCarSuccess.route) {
                AddCarSuccess(
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}