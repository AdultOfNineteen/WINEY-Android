package com.teamwiney.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations


fun NavGraphBuilder.homeGraph(
    appState: WineyAppState,
    homeViewModel: HomeViewModel,
) {

    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            HomeScreen(
                appState = appState,
                viewModel = homeViewModel
            )
        }

        composable(route = HomeDestinations.WINE_TIP) {
            WineTipScreen(
                appState = appState,
                viewModel = homeViewModel
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
            WineDetailScreen(
                appState = appState,
                wineId = entry.arguments?.getLong("wineId") ?: 0L,
                viewModel = homeViewModel
            )
        }
    }
}
