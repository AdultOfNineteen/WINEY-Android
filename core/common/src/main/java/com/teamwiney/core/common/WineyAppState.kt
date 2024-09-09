package com.teamwiney.core.common

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.rememberCameraPositionState
import com.teamwiney.core.common.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberWineyAppState(
    networkMonitor: NetworkMonitor,
    isMapDetail: MutableState<Boolean> = mutableStateOf(false),
    isLoggedIn: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    cameraPositionState: CameraPositionState = rememberCameraPositionState()
): WineyAppState {
    return remember(Unit) {
        WineyAppState(
            networkMonitor,
            isMapDetail,
            isLoggedIn,
            navController,
            scaffoldState,
            scope,
            cameraPositionState
        )
    }
}

@Stable
class WineyAppState(
    networkMonitor: NetworkMonitor,
    val isMapDetail: MutableState<Boolean>,
    val isLoggedIn: MutableState<Boolean>,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val cameraPositionState: CameraPositionState
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestination = TopLevelDestination.values().toList()

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val shouldShowBottomBar: Boolean
        @Composable get() = !isMapDetail.value && currentDestination?.route ==
                topLevelDestination.find { it.route == currentDestination?.route }?.route


    fun updateIsMapDetail(isMapDetail: Boolean) {
        this.isMapDetail.value = isMapDetail
    }

    fun showSnackbar(message: String) = scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(message)
    }

    fun navigate(destination: String, builder: NavOptionsBuilder.() -> Unit = {}) {
        navController.navigate(destination, builder)
    }

    fun navigate(
        destination: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        navController.navigate(destination, navOptions, navigatorExtras)
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        navController.navigate(topLevelDestination.route, navOptions)
    }
}