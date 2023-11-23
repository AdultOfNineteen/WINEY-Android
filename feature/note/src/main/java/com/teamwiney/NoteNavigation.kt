package com.teamwiney

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.notecollection.NoteFilterScreen
import com.teamwiney.notecollection.NoteScreen
import com.teamwiney.notecollection.NoteViewModel
import com.teamwiney.notedetail.NoteDetailScreen
import com.teamwiney.notewrite.noteWriteGraph

fun NavGraphBuilder.noteGraph(
    appState: WineyAppState,
    noteViewModel: NoteViewModel,
    bottomSheetState: WineyBottomSheetState
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
                bottomSheetState = bottomSheetState,
                viewModel = noteViewModel,
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
                viewModel = noteViewModel,
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
                bottomSheetState = bottomSheetState,
            )
        }

        noteWriteGraph(
            appState = appState,
            bottomSheetState = bottomSheetState
        )
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