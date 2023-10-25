package com.teamwiney.winey

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import com.teamwiney.auth.authGraph
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.TopLevelDestination
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.createnote.mapGraph
import com.teamwiney.home.HomeViewModel
import com.teamwiney.home.homeGraph
import com.teamwiney.mypage.myPageGraph
import com.teamwiney.notecollection.NoteViewModel
import com.teamwiney.notecollection.noteGraph
import com.teamwiney.ui.components.BottomNavigationBar
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost() {
    var onHideBottomSheet by remember {
        mutableStateOf<() -> Unit>({})
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var bottomSheetContent: SheetContent? by remember { mutableStateOf(null) }

    val showBottomSheet: (SheetContent) -> Unit = { content: SheetContent ->
        keyboardController?.hide()
        bottomSheetContent = content
    }
    val hideBottomSheet: () -> Unit = {
        keyboardController?.hide()
        bottomSheetContent = null
    }

    val appState = rememberWineyAppState(
        bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {
                if (it == ModalBottomSheetValue.Hidden) {
                    onHideBottomSheet()
                }
                true
            },
            skipHalfExpanded = true
        ),
        setBottomSheet = showBottomSheet,
        clearBottomSheet = hideBottomSheet
    )

    val navController = appState.navController

    // 메인화면 뷰 모델들
    val noteViewModel: NoteViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    ModalBottomSheetLayout(
        sheetContent = {
            bottomSheetContent?.invoke(this)
        },
        sheetState = appState.bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
        modifier = Modifier.navigationBarsPadding()
    ) {
        Scaffold(
            backgroundColor = WineyTheme.colors.background_1,
            snackbarHost = {
                SnackbarHost(hostState = appState.scaffoldState.snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            modifier = Modifier.padding(
                                bottom = 50.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                        ) {
                            Text(text = data.message)
                        }
                    }
                )
            },
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
                    showBottomSheet = appState::showBottomSheet,
                    hideBottomSheet = appState::hideBottomSheet,
                    setOnHideBottomSheet = { onHide ->
                        onHideBottomSheet = onHide
                    }
                )
                homeGraph(
                    appState = appState,
                    showBottomSheet = appState::showBottomSheet,
                    hideBottomSheet = appState::hideBottomSheet
                )
                mapGraph(
                    navController = navController,
                    showBottomSheet = appState::showBottomSheet,
                    hideBottomSheet = appState::hideBottomSheet
                )
                noteGraph(
                    appState = appState,
                    noteViewModel = noteViewModel,
                    showBottomSheet = appState::showBottomSheet,
                    hideBottomSheet = appState::hideBottomSheet
                )
                myPageGraph(
                    navController = navController,
                    showBottomSheet = appState::showBottomSheet,
                    hideBottomSheet = appState::hideBottomSheet
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

@Composable
fun rememberNavControllerBackStackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}