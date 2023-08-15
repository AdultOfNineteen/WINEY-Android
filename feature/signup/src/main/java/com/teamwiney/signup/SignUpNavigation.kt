package com.teamwiney.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.LoginDestinations
import com.teamwiney.core.common.SignUpDestinations

fun NavGraphBuilder.signUpGraph(
    navController: NavController
) {
    navigation(
        route = SignUpDestinations.ROUTE,
        startDestination = SignUpDestinations.PHONE
    ) {
        composable(route = SignUpDestinations.PHONE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SignUpDestinations.ROUTE
            )
            SignUpPhoneScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { navController.navigate(SignUpDestinations.AUTHENTICATION) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SignUpDestinations.AUTHENTICATION) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SignUpDestinations.ROUTE
            )
            SignUpAuthenticationScreen(
                onBack = {
                    navController.navigate(LoginDestinations.ROUTE) {
                        popUpTo(SignUpDestinations.ROUTE) { inclusive = true }
                    }
                },
                onSend = { },
                onConfirm = { navController.navigate(SignUpDestinations.FAVORITE_TASTE) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SignUpDestinations.FAVORITE_TASTE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SignUpDestinations.ROUTE
            )
            SignUpFavoriteTasteScreen(
                onBack = {
                    navController.navigate(LoginDestinations.ROUTE) {
                        popUpTo(SignUpDestinations.ROUTE) { inclusive = true }
                    }
                },
                onConfirm = {},
                onSelectionComplete = { navController.navigate(SignUpDestinations.COMPLETE) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SignUpDestinations.COMPLETE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SignUpDestinations.ROUTE
            )
            SignUpCompleteScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { /*TODO*/ },
                viewModel = hiltViewModel(backStackEntry)
            )
        }
    }
}

@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}