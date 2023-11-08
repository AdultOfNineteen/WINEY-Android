package com.teamwiney.notewrite

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.rememberNavControllerBackStackEntry

fun NavGraphBuilder.noteWriteGraph(
    appState: WineyAppState,
    wineyBottomSheetState: WineyBottomSheetState
) {
    navigation(
        route = NoteDestinations.Write.ROUTE,
        startDestination = NoteDestinations.Write.SEARCH_WINE
    ) {
        composable(route = NoteDestinations.Write.SEARCH_WINE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineSearchScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                wineyBottomSheetState = wineyBottomSheetState,
            )
        }

        composable(route = NoteDestinations.Write.SELECT_WINE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWriteSelectWineScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                wineyBottomSheetState = wineyBottomSheetState,
            )
        }
    }
}