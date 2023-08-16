package com.teamwiney.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.HomeDestinations
import com.teamwiney.ui.signup.SheetContent

// TODO 메인 그래프라고 생각하고 만들었는데 Mypage, Note 모듈이 따로
//  있어서 이걸 어떻게 처리할까유
fun NavGraphBuilder.mainGraph(
    navController: NavController,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = HomeDestinations.ROUTE,
        startDestination = HomeDestinations.HOME
    ) {
        composable(route = HomeDestinations.HOME) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = HomeDestinations.ROUTE
            )
            HomeScreen()
        }

        composable(route = HomeDestinations.MAP) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = HomeDestinations.ROUTE
            )
            MapScreen()
        }

        composable(route = HomeDestinations.NOTE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = HomeDestinations.ROUTE
            )
            NoteScreen()
        }

        composable(route = HomeDestinations.MY_PAGE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = HomeDestinations.ROUTE
            )
            // MyPageScreen()
        }
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