package com.teamwiney.winey

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import com.teamwiney.analysis.analysisGraph
import com.teamwiney.auth.authGraph
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.TopLevelDestination
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.createnote.mapGraph
import com.teamwiney.home.HomeViewModel
import com.teamwiney.home.homeGraph
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.mypage.myPageGraph
import com.teamwiney.noteGraph
import com.teamwiney.notecollection.NoteViewModel
import com.teamwiney.ui.components.BottomNavigationBar
import com.teamwiney.ui.components.BottomNavigationItem
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost() {
    val appState = rememberWineyAppState()
    val bottomSheetState = rememberWineyBottomSheetState()
    val navController = appState.navController

    // 메인화면 뷰 모델들
    val homeViewModel: HomeViewModel = viewModel()
    val noteViewModel: NoteViewModel = viewModel()
    val myPageViewModel: MyPageViewModel = viewModel()

    // 초기 메인 화면 진입 시 수행해야할 작업을 정의합니다.
    val onInit: () -> Unit = {
        homeViewModel.getRecommendWines()
        homeViewModel.getWineTips()
        noteViewModel.getTastingNotes()
        myPageViewModel.getUserWineGrade()
        myPageViewModel.getWineGradeStandard()
    }

    ModalBottomSheetLayout(
        sheetContent = {
            bottomSheetState.bottomSheetContent.value?.invoke(this)
        },
        sheetState = bottomSheetState.bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
        modifier = Modifier.navigationBarsPadding()
    ) {
        Scaffold(
            backgroundColor = WineyTheme.colors.background_1,
            snackbarHost = {
                WineySnackBar(appState)
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
                    bottomSheetState = bottomSheetState,
                    onInit = onInit
                )
                homeGraph(
                    appState = appState,
                    homeViewModel = homeViewModel,
                )
                mapGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState,
                )
                noteGraph(
                    appState = appState,
                    noteViewModel = noteViewModel,
                    bottomSheetState = bottomSheetState,
                )
                myPageGraph(
                    appState = appState,
                    myPageViewModel = myPageViewModel,
                    bottomSheetState = bottomSheetState,
                )
                analysisGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )
            }
        }
    }
}


@Composable
private fun WineySnackBar(appState: WineyAppState) {
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

            BottomNavigationItem(
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