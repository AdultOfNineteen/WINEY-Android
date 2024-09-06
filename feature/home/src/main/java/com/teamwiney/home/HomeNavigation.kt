package com.teamwiney.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.winedetail.WineDetailScreen


fun NavGraphBuilder.homeGraph(
    appState: WineyAppState
) {
    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            HomeScreen(
                appState = appState,
                viewModel = hiltViewModel()
            )
        }

        composable(route = HomeDestinations.WINE_TIP) {
            WineTipScreen(
                appState = appState,
                viewModel = hiltViewModel()
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
        ) {
            WineDetailScreen(
                appState = appState,
                viewModel = hiltViewModel()
            )
        }
    }
}