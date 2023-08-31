package com.teamwiney.winey

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import com.teamwiney.auth.authGraph
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.core.common.domain.common.TopLevelDestination
import com.teamwiney.core.common.domain.common.rememberWineyAppState
import com.teamwiney.createnote.mapGraph
import com.teamwiney.home.homeGraph
import com.teamwiney.mypage.myPageGraph
import com.teamwiney.notecollection.noteGraph
import com.teamwiney.ui.components.BottomNavigationBar
import com.teamwiney.ui.signup.SheetContent
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost() {
    val appState = rememberWineyAppState()
    val navController = appState.navController

    var onHideBottomSheet by remember {
        mutableStateOf<() -> Unit>({})
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                onHideBottomSheet()
            }
            true
        },
        skipHalfExpanded = true
    )
    var bottomSheetContent: SheetContent? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    val showBottomSheet: (SheetContent) -> Unit = { content: SheetContent ->
        keyboardController?.hide()
        bottomSheetContent = content
        scope.launch { bottomSheetState.show() }
    }
    val hideBottomSheet: () -> Unit = {
        scope.launch {
            keyboardController?.hide()
            bottomSheetState.hide()
            bottomSheetContent = null
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            bottomSheetContent?.invoke(this)
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
    ) {
        Scaffold(
            backgroundColor = WineyTheme.colors.background_1,
            bottomBar = {
                AnimatedVisibility(
                    appState.shouldShowBottomBar,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it },
                ) {
                    WineyBottomNavigationBar(
                        destinations = appState.topLevelDestination,
                        currentDestination = appState.currentDestination,
                        onNavigateToDestination = appState::navigateToTopLevelDestination
                    )
                }
            }
        ) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = AuthDestinations.ROUTE
            ) {
                authGraph(
                    appState = appState,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet,
                    setOnHideBottomSheet = { event ->
                        onHideBottomSheet = event
                    }
                )
                homeGraph(
                    navController = navController,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet,
                    setOnHideBottomSheet = { event ->
                        onHideBottomSheet = event
                    }
                )
                mapGraph(
                    navController = navController,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet,
                    setOnHideBottomSheet = { event ->
                        onHideBottomSheet = event
                    }
                )
                noteGraph(
                    navController = navController,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet,
                    setOnHideBottomSheet = { event ->
                        onHideBottomSheet = event
                    }
                )
                myPageGraph(
                    navController = navController,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet,
                    setOnHideBottomSheet = { event ->
                        onHideBottomSheet = event
                    }
                )
            }
        }
    }
}

@Composable
private fun WineyBottomNavigationBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    backgroundColor: Color = WineyTheme.colors.gray_900,
    selectedContentColor: Color = WineyTheme.colors.gray_50,
    unselectedContentColor: Color = WineyTheme.colors.gray_800
) {
    BottomNavigationBar(
        modifier = modifier,
        backgroundColor = backgroundColor
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination?.route == destination.route

            com.teamwiney.ui.components.BottomNavigationItem(
                label = destination.label,
                selected = selected,
                selectedIcon = destination.selectedIcon,
                unselectedIcon = destination.unselectedIcon,
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
                onClick = { onNavigateToDestination(destination) },
            )
        }
    }
}
