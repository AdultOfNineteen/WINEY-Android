package com.teamwiney.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.SignUpDestinations
import com.teamwiney.ui.signup.SheetContent

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
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
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                onHideBottomSheet = setOnHideBottomSheet,
                navController = navController,
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
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                navController = navController,
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
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                navController = navController,
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
                navController = navController,
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