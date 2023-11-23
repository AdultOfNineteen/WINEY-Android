package com.teamwiney.createnote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MapDestinations

fun NavGraphBuilder.mapGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState
) {
    navigation(
        route = MapDestinations.ROUTE,
        startDestination = MapDestinations.MAP
    ) {
        composable(route = MapDestinations.MAP) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MapDestinations.ROUTE
            )
            MapScreen()
        }

        // TODO : 매장 상세 정보?
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