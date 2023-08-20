package com.teamwiney.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.ui.signup.SheetContent

fun NavGraphBuilder.signUpGraph(
    navController: NavController,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = AuthDestinations.SignUp.ROUTE,
        startDestination = AuthDestinations.SignUp.PHONE
    ) {
        composable(route = AuthDestinations.SignUp.PHONE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = AuthDestinations.SignUp.ROUTE
            )
            SignUpPhoneScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                onHideBottomSheet = setOnHideBottomSheet,
                navController = navController,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.AUTHENTICATION) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = AuthDestinations.SignUp.ROUTE
            )
            SignUpAuthenticationScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                navController = navController,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.FAVORITE_TASTE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = AuthDestinations.SignUp.ROUTE
            )
            SignUpFavoriteTasteScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                navController = navController,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.COMPLETE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = AuthDestinations.SignUp.ROUTE
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