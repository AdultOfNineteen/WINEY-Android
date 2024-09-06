package com.teamwiney.winey

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.teamwiney.analysis.analysisGraph
import com.teamwiney.auth.authGraph
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.navigation.MapDestinations
import com.teamwiney.core.common.navigation.ReusableDestinations
import com.teamwiney.core.common.navigation.TopLevelDestination
import com.teamwiney.home.WebViewScreen
import com.teamwiney.home.homeGraph
import com.teamwiney.map.mapGraph
import com.teamwiney.mypage.myPageGraph
import com.teamwiney.noteGraph
import com.teamwiney.ui.components.BottomNavigationBar
import com.teamwiney.ui.components.BottomNavigationItem
import com.teamwiney.ui.theme.WineyTheme

private var isReceiverRegistered = false

@Composable
fun TokenExpiredBroadcastReceiver(
    onExpired: (intent: Intent?) -> Unit
) {
    val context = LocalContext.current
    val currentOnExpired by rememberUpdatedState(onExpired)

    DisposableEffect(context) {
        val intentFilter = IntentFilter("com.teamwiney.winey.TOKEN_EXPIRED")
        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                currentOnExpired(intent)
            }
        }

        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcast, intentFilter)
            isReceiverRegistered = true
            Log.d("debugging", "리시버 부착")
        }

        onDispose {
            if (isReceiverRegistered) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcast)
                isReceiverRegistered = false
                Log.d("debugging", "리시버 부착 해제")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState
) {
    val navController = appState.navController

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    LaunchedEffect(isOffline) {
        if (isOffline) {
            appState.scaffoldState.snackbarHostState.showSnackbar(
                message = "⚠\uFE0F 인터넷에 연결되어 있지 않습니다.",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    TokenExpiredBroadcastReceiver { intent ->
        if (intent?.action == "com.teamwiney.winey.TOKEN_EXPIRED") {
            appState.showSnackbar("토큰이 만료되었습니다. 다시 로그인해주세요.")
            appState.navController.navigate(
                AuthDestinations.Login.LOGIN,
                navOptions {
                    popUpTo(AuthDestinations.Login.LOGIN) {
                        inclusive = true
                    }
                }
            )
        }
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
                    bottomSheetState = bottomSheetState,
                    kakaoLinkScheme = BuildConfig.KAKAO_LINK_SCHEME
                )
                myPageGraph(
                    appState = appState,
                    bottomSheetState = bottomSheetState,
                    versionName = BuildConfig.VERSION_NAME
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
                    bottom = 20.dp,
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