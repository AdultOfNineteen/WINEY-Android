package com.teamwiney.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MyPageDestinations

fun NavGraphBuilder.myPageGraph(
    appState: WineyAppState,
    myPageViewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    navigation(
        route = MyPageDestinations.ROUTE,
        startDestination = MyPageDestinations.MY_PAGE
    ) {
        composable(route = MyPageDestinations.MY_PAGE) {
            MyPageScreen(
                appState = appState,
                viewModel = myPageViewModel
            )
        }

        composable(route = MyPageDestinations.BADGE) {
            MyPageBadgeScreen(
                appState = appState
            )
        }

        composable(route = MyPageDestinations.ACCOUNT) {
            MyPageAccountScreen(
                appState = appState,
                viewModel = myPageViewModel,
                bottomSheetState = bottomSheetState
            )
        }

        composable(route = MyPageDestinations.WITHDRAWAL_REASON_SELECT) {
            MyPageWithdrawalReasonSelectScreen(
                appState = appState,
                viewModel = myPageViewModel,
                bottomSheetState = bottomSheetState
            )
        }

        // TODO : 프로필


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