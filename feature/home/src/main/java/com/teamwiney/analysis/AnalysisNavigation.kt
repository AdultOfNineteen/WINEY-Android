package com.teamwiney.analysis

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.HomeDestinations

fun NavGraphBuilder.analysisGraph(
    appState: WineyAppState,
    wineyBottomSheetState: WineyBottomSheetState,
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
                wineyBottomSheetState = wineyBottomSheetState
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