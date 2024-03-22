package com.teamwiney.winey

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teamwiney.analysis.analysisGraph
import com.teamwiney.auth.authGraph
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.navigation.MapDestinations
import com.teamwiney.core.common.navigation.ReusableDestinations
import com.teamwiney.core.common.navigation.TopLevelDestination
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.rememberWineyBottomSheetState
import com.teamwiney.home.WebViewScreen
import com.teamwiney.home.homeGraph
import com.teamwiney.map.mapGraph
import com.teamwiney.mypage.myPageGraph
import com.teamwiney.noteGraph
import com.teamwiney.ui.components.BottomNavigationBar
import com.teamwiney.ui.components.BottomNavigationItem
import com.teamwiney.ui.theme.WineyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost() {
    val appState = rememberWineyAppState()
    val bottomSheetState = rememberWineyBottomSheetState()
    val navController = appState.navController

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
                modifier = Modifier
                    .bottomBarPadding(appState.currentDestination, padding),
                navController = navController,
                startDestination = AuthDestinations.ROUTE
            ) {
                authGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )
                homeGraph(
                    appState = appState
                )
                mapGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )
                noteGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )
                myPageGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )
                analysisGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState
                )

                composable(
                    route = "${ReusableDestinations.WEB_VIEW}?url={url}&title={title}&subTitle={subTitle}",
                    arguments = listOf(
                        navArgument("url") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("title") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("subTitle") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) { entry ->
                    WebViewScreen(
                        appState = appState,
                        url = entry.arguments?.getString("url") ?: "",
                        title = entry.arguments?.getString("title") ?: "",
                        subTitle = entry.arguments?.getString("subTitle") ?: ""
                    )
                }
            }
        }
    }
}

@Composable
private fun Modifier.Companion.bottomBarPadding(
    currentDestination: NavDestination?,
    padding: PaddingValues
): Modifier {
    Log.i("dlgocks1", currentDestination?.route.toString())
    return if (currentDestination?.route in listOf(
            MapDestinations.MAP,
            HomeDestinations.Analysis.START,
            HomeDestinations.Analysis.RESULT
        )
    ) {
        Modifier
    } else {
        Modifier.padding(padding)
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
                onClick = {
                    if (!selected) {
                        onNavigateToDestination(destination)
                    }
                },
            )
        }
    }
}