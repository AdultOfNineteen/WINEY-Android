package com.teamwiney.winey

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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.teamwiney.core.common.SplashDestinations
import com.teamwiney.login.loginGraph
import com.teamwiney.signup.signUpGraph
import com.teamwiney.splash.splashComposable
import com.teamwiney.ui.signup.SheetContent
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineyNavHost() {
    val appState = rememberWineyAppState()
    val navController = appState.navController

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    var bottomSheetContent: SheetContent? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    val showBottomSheet: (SheetContent) -> Unit = { content: SheetContent ->
        bottomSheetContent = content
        scope.launch { bottomSheetState.show() }
    }
    val hideBottomSheet: () -> Unit = {
        scope.launch {
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
                if (appState.shouldShowBottomBar) {
                    // TODO : 바텀 네비게이션 바
                }
            }
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = SplashDestinations.ROUTE
            ) {
                splashComposable(
                    navController = navController
                )
                loginGraph(
                    navController = navController
                )
                signUpGraph(
                    navController = navController,
                    showBottomSheet = showBottomSheet,
                    hideBottomSheet = hideBottomSheet
                )
            }
        }
    }
}
