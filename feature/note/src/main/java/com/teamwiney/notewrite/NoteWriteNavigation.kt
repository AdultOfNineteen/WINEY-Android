package com.teamwiney.notewrite

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        route = "${NoteDestinations.Write.ROUTE}?noteId={noteId}",
        startDestination = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
    ) {
        composable(
            route = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
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
                graph = "${NoteDestinations.Write.SEARCH_WINE}?noteId={noteId}"
            )
            NoteWineInfoMemoScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }
    }
}