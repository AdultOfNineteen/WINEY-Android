package com.teamwiney.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

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
            HomeScreen(appState = appState)
        }

        composable(route= HomeDestinations.DETAIL) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            DetailScreen()
        }

        // TODO : 오늘의 와인 추천

        // TODO : 와인 초보를 위한 TIP

        // TODO : 와인 취향 분석하기
        composable(route = HomeDestinations.ANAYLYSIS) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = appState.navController,
                graph = HomeDestinations.ROUTE
            )
            AnalysisScreen()
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