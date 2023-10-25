package com.teamwiney.core.common

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import com.teamwiney.core.common.navigation.TopLevelDestination
import com.teamwiney.core.common.`typealias`.SheetContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberWineyAppState(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    setBottomSheet: (SheetContent) -> Unit,
    clearBottomSheet: () -> Unit
): WineyAppState {
    return remember(Unit) {
        WineyAppState(
            navController,
            scaffoldState,
            scope,
            bottomSheetState,
            setBottomSheet,
            clearBottomSheet
        )
    }
}

@Stable
class WineyAppState @OptIn(ExperimentalMaterialApi::class) constructor(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val bottomSheetState: ModalBottomSheetState,
    val setBottomSheet: (SheetContent) -> Unit,
    val clearBottomSheet: () -> Unit
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestination = TopLevelDestination.values().toList()

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route ==
                topLevelDestination.find { it.route == currentDestination?.route }?.route

    @OptIn(ExperimentalMaterialApi::class)
    fun showBottomSheet(content: SheetContent) = scope.launch {
        setBottomSheet(content)
        bottomSheetState.show()
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun hideBottomSheet() = scope.launch {
        clearBottomSheet()
        bottomSheetState.hide()
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