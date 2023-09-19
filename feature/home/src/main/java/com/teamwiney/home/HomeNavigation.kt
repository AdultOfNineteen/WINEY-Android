package com.teamwiney.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.home.analysis.AnalysisResultScreen
import com.teamwiney.home.analysis.AnalysisScreen

fun NavGraphBuilder.homeGraph(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            val backStackEntry = rememberNavControllerBackEntry(
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
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            WineTipScreen(
                appState = appState
            )
        }

        composable(route = HomeDestinations.WINE_DETAIL) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            WineDetailScreen(
                appState = appState
            )
        }
    }

    navigation(
        route = HomeDestinations.Analysis.ROUTE,
        startDestination = HomeDestinations.Analysis.START
    ) {
        composable(
            route = HomeDestinations.Analysis.START,
        ) {
//            val backStackEntry = rememberNavControllerBackEntry(
//                entry = it,
//                navController = appState.navController,
//                graph = HomeDestinations.ROUTE,
//            )
            AnalysisScreen(
                appState = appState,
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
            )
        }

        composable(
            route = HomeDestinations.Analysis.RESULT,
        ) {
            AnalysisResultScreen(
                appState = appState,
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