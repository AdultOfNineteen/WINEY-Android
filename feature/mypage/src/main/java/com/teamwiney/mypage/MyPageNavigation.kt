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
import com.teamwiney.core.common.util.Constants.PRIVACY_POLICY_URL
import com.teamwiney.core.common.util.Constants.TERMS_OF_USE_URL
import com.teamwiney.mypage.account.MyPageAccountScreen
import com.teamwiney.mypage.account.MyPageWithdrawalConfirmScreen
import com.teamwiney.mypage.account.MyPageWithdrawalReasonSelectScreen
import com.teamwiney.mypage.badge.MyPageBadgeScreen

fun NavGraphBuilder.myPageGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState
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
                viewModel = hiltViewModel(backStackEntry)
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

        composable(route = MyPageDestinations.TERMS_OF_USE) {
            MyPageTermsScreen(
                appState = appState,
                title = "서비스 이용약관",
                url = TERMS_OF_USE_URL
            )
        }

        composable(route = MyPageDestinations.PRIVACY_POLICY) {
            MyPageTermsScreen(
                appState = appState,
                title = "개인정보 처리방침",
                url = PRIVACY_POLICY_URL
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