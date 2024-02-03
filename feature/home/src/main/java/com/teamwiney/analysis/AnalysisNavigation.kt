package com.teamwiney.analysis

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.HomeDestinations

fun NavGraphBuilder.analysisGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
) {
    navigation(
        route = HomeDestinations.Analysis.ROUTE,
        startDestination = HomeDestinations.Analysis.START
    ) {
        composable(
            route = HomeDestinations.Analysis.START,
        ) {
            val viewModel: AnalysisViewModel = hiltViewModel()

            AnalysisScreen(
                appState = appState,
                bottomSheetState = bottomSheetState,
                viewModel = viewModel
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