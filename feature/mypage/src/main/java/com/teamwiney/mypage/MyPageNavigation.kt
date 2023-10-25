package com.teamwiney.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.core.common.`typealias`.SheetContent

fun NavGraphBuilder.myPageGraph(
    navController: NavController,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit
) {
    navigation(
        route = MyPageDestinations.ROUTE,
        startDestination = MyPageDestinations.MY_PAGE
    ) {
        composable(route = MyPageDestinations.MY_PAGE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageScreen()
        }

        // TODO : 프로필

        // TODO : 설정

        // TODO : 버전 정보

        // TODO : 고객센터
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