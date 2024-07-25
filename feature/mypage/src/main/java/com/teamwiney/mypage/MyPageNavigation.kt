package com.teamwiney.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.mypage.account.MyPageAccountScreen
import com.teamwiney.mypage.account.MyPageModifyNicknameScreen
import com.teamwiney.mypage.account.MyPageWithdrawalConfirmScreen
import com.teamwiney.mypage.account.MyPageWithdrawalReasonSelectScreen
import com.teamwiney.mypage.badge.MyPageBadgeScreen

fun NavGraphBuilder.myPageGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
    versionName: String
) {
    navigation(
        route = MyPageDestinations.ROUTE,
        startDestination = MyPageDestinations.MY_PAGE
    ) {
        composable(route = MyPageDestinations.MY_PAGE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                versionName = versionName
            )
        }

        composable(route = MyPageDestinations.BADGE) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageBadgeScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                bottomSheetState = bottomSheetState
            )
        }

        composable(route = MyPageDestinations.ACCOUNT) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageAccountScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                bottomSheetState = bottomSheetState
            )
        }

        composable(route = MyPageDestinations.MODIFY_NICKNAME) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageModifyNicknameScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = MyPageDestinations.WITHDRAWAL_REASON_SELECT) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageWithdrawalReasonSelectScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                bottomSheetState = bottomSheetState
            )
        }

        composable(route = MyPageDestinations.WITHDRAWAL_CONFIRM) {
            val backStackEntry = rememberNavControllerBackStackEntry(
                entry = it,
                navController = appState.navController,
                graph = MyPageDestinations.ROUTE
            )
            MyPageWithdrawalConfirmScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                bottomSheetState = bottomSheetState
            )
        }
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