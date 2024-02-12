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
    bottomSheetState: WineyBottomSheetState
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
                viewModel = hiltViewModel(backStackEntry)
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
                bottomSheetState = bottomSheetState
            )
        }

        composable(route = NoteDestinations.Write.INFO_LEVEL) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoLevelScreen(
                appState = appState,
                bottomSheetState = bottomSheetState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = NoteDestinations.Write.INFO_VINTAGE_AND_PRICE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoVintageAndPriceScreen(
                appState = appState,
                bottomSheetState = bottomSheetState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = NoteDestinations.Write.INFO_COLOR_SMELL) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoColorAndSmellScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
            )
        }

        composable(route = NoteDestinations.Write.INFO_FLAVOR) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoFlavorScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = NoteDestinations.Write.INFO_STANDARD_SMELL) {
            NoteWineInfoStandardSmellScreen(
                appState = appState
            )
        }


        composable(route = NoteDestinations.Write.INFO_STANDARD_FLAVOR) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoStandardFlavorScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }


        composable(route = NoteDestinations.Write.INFO_MEMO) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteWineInfoMemoScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }
    }
}