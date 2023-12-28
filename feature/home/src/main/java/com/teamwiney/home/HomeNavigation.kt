package com.teamwiney.home

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
import com.teamwiney.core.common.navigation.HomeDestinations


fun NavGraphBuilder.homeGraph(
    appState: WineyAppState
) {
    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            HomeScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = HomeDestinations.WINE_TIP) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            WineTipScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(
            route = "${HomeDestinations.WINE_TIP_DETAIL}?url={url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            WebViewScreen(
                appState = appState,
                url = entry.arguments?.getString("url") ?: ""
            )
        }

        composable(
            route = "${HomeDestinations.WINE_DETAIL}?id={wineId}",
            arguments = listOf(
                navArgument("wineId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { entry ->
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = entry,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            WineDetailScreen(
                appState = appState,
                wineId = entry.arguments?.getLong("wineId") ?: 0L,
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