package com.teamwiney.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

fun NavGraphBuilder.signUpGraph(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = "${AuthDestinations.SignUp.ROUTE}/{userId}",
        startDestination = "${AuthDestinations.SignUp.PHONE}/{userId}"
    ) {
        composable(
            route = "${AuthDestinations.SignUp.PHONE}/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) { entry ->
            val userId = entry.arguments?.getString("userId") ?: ""
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = entry,
                navController = appState.navController,
                graph = "${AuthDestinations.SignUp.PHONE}/{userId}"
            )
            SignUpPhoneScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                onHideBottomSheet = setOnHideBottomSheet,
                appState = appState,
                userId = userId,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.AUTHENTICATION) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = "${AuthDestinations.SignUp.PHONE}/{userId}"
            )
            SignUpAuthenticationScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.FAVORITE_TASTE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = "${AuthDestinations.SignUp.PHONE}/{userId}"
            )
            SignUpFavoriteTasteScreen(
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = AuthDestinations.SignUp.COMPLETE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = "${AuthDestinations.SignUp.PHONE}/{userId}"
            )
            SignUpCompleteScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }
    }
}

@Composable
fun rememberNavControllerBackStackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}