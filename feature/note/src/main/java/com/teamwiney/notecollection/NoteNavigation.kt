package com.teamwiney.notecollection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.notedetail.NoteDetailScreen

fun NavGraphBuilder.noteGraph(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = NoteDestinations.ROUTE,
        startDestination = NoteDestinations.NOTE
    ) {
        composable(route = NoteDestinations.NOTE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteScreen(
                appState = appState,
                showBottomSheet = showBottomSheet,
                viewModel = hiltViewModel(backStackEntry),
                hideBottomSheet = hideBottomSheet,
            )
        }

        composable(route = NoteDestinations.FILTER) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = NoteDestinations.ROUTE
            )
            NoteFilterScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
            )
        }


        composable(
            route = "${NoteDestinations.DETAIL}?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            NoteDetailScreen(
                noteId = it.arguments?.getInt("noteId") ?: 0,
                appState = appState,
                showBottomSheet = showBottomSheet,
                hideBottomSheet = hideBottomSheet,
            )
        }

        // TODO : 노트 작성
    }
}

@Composable
fun rememberNavControllerBackStackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}