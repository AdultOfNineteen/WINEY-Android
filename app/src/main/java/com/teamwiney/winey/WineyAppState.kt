package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teamwiney.core.common.HomeDestinations

@Composable
fun rememberWineyAppState(
    navController: NavHostController = rememberNavController()
) : WineyAppState {
    return remember(navController) { WineyAppState(navController) }
}

class WineyAppState(val navController: NavHostController) {
    val currentDestinations: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestination = listOf(
        HomeDestinations.ROUTE
    )

    // 탭의 각 최상단에 해당할 때만 바텀 바를 노출
    val shouldShowBottomBar: Boolean
        @Composable get() = topLevelDestination
            .contains(currentDestinations?.route)
}