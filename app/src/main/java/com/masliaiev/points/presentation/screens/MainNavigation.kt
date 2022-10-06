package com.masliaiev.points.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.masliaiev.points.presentation.screens.add_point.AddPointScreen
import com.masliaiev.points.presentation.screens.add_point.AddPointScreenViewModel
import com.masliaiev.points.presentation.screens.login.LoginScreen
import com.masliaiev.points.presentation.screens.login.LoginViewModel
import com.masliaiev.points.presentation.screens.map.AppMapScreen
import com.masliaiev.points.presentation.screens.map.AppMapViewModel
import com.masliaiev.points.presentation.screens.points_list.PointsListScreen
import com.masliaiev.points.presentation.screens.points_list.PointsListScreenViewModel
import com.masliaiev.points.presentation.screens.profile.ProfileScreen
import com.masliaiev.points.presentation.screens.profile.ProfileScreenViewModel
import com.masliaiev.points.presentation.screens.signup.SignUpScreen
import com.masliaiev.points.presentation.screens.signup.SignUpViewModel
import com.masliaiev.points.presentation.screens.splash_screen.SplashScreen
import com.masliaiev.points.presentation.screens.splash_screen.SplashScreenViewModel

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.SplashScreen.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screens.SplashScreen.route) {
            val viewModel = hiltViewModel<SplashScreenViewModel>()
            SplashScreen(
                viewModel = viewModel,
                onNavigateToAppMap = {
                    navController.navigate(route = Screens.AppMap.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToLogIn = {
                    navController.navigate(route = Screens.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screens.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                onNavigateToAppMap = {
                    navController.navigate(route = Screens.AppMap.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(route = Screens.SignUp.route)
                }
            )
        }

        composable(Screens.SignUp.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(
                viewModel = viewModel,
                onNavigateToAppMap = {
                    navController.navigate(route = Screens.AppMap.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToLogIn = {
                    navController.navigate(route = Screens.Login.route)
                }
            )
        }

        composable(Screens.AppMap.route) {
            val viewModel = hiltViewModel<AppMapViewModel>()
            AppMapScreen(
                viewModel = viewModel,
                onNavigateToProfile = { userLogin ->
                    navController.navigate(route = Screens.Profile.getRouteWithArgument(userLogin))
                },
                onNavigateToPointsList = {
                    navController.navigate(Screens.PointsList.route)
                },
                onNavigateToAddPoint = {userLogin, latitude, longitude ->
                    navController.navigate(route = Screens.AddPoint.getRouteWithArgument(userLogin, latitude, longitude))
                }
            )
        }

        composable(Screens.Profile.route) { backStackEntry ->
            val viewModel = hiltViewModel<ProfileScreenViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                userLogin = backStackEntry.arguments?.getString(Screens.loginArgument),
                onNavigateToLogIn = {
                    navController.navigate(route = Screens.Login.route) {
                        popUpTo(0)
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screens.PointsList.route) {
            val viewModel = hiltViewModel<PointsListScreenViewModel>()
            PointsListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screens.AddPoint.route) { backStackEntry ->
            val viewModel = hiltViewModel<AddPointScreenViewModel>()
            AddPointScreen(
                viewModel = viewModel,
                userLogin = backStackEntry.arguments?.getString(Screens.loginArgument),
                onSaveClick = {
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                longitude = backStackEntry.arguments?.getString(Screens.longitude)?.toDouble(),
                latitude = backStackEntry.arguments?.getString(Screens.latitude)?.toDouble()
            )
        }
    }
}