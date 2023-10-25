package com.teamwiney.analysis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

fun NavGraphBuilder.analysisGraph(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit
) {
    navigation(
        route = HomeDestinations.Analysis.ROUTE,
        startDestination = HomeDestinations.Analysis.START
    ) {
        composable(
            route = HomeDestinations.Analysis.START,
        ) {
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