package com.teamwiney.notecollection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

fun NavGraphBuilder.noteGraph(
    navController: NavController,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = NoteDestinations.ROUTE,
        startDestination = NoteDestinations.NOTE
    ) {
        composable(route = NoteDestinations.NOTE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = NoteDestinations.ROUTE
            )
            NoteScreen(
                showBottomSheet = showBottomSheet,
                viewModel = hiltViewModel(backStackEntry),
                hideBottomSheet = hideBottomSheet,
            )
        }

        // TODO : 노트 작성 / 수정

        // TODO : 노트 상세보기
    }
}

@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}