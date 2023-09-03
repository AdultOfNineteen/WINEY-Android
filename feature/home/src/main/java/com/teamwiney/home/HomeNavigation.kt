package com.teamwiney.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

// TODO 메인 그래프라고 생각하고 만들었는데 Mypage, Note 모듈이 따로
//  있어서 이걸 어떻게 처리할까유
fun NavGraphBuilder.homeGraph(
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

        // TODO : 오늘의 와인 추천

        // TODO : 와인 초보를 위한 TIP

        // TODO : 와인 취향 분석하기
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