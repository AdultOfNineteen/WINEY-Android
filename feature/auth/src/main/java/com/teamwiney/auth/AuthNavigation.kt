package com.teamwiney.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.auth.login.loginGraph
import com.teamwiney.auth.signup.signUpGraph
import com.teamwiney.auth.splash.SplashScreen
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations

fun NavGraphBuilder.authGraph(
    appState: WineyAppState,
    wineyBottomSheetState: WineyBottomSheetState,
) {
    navigation(
        route = AuthDestinations.ROUTE,
        startDestination = AuthDestinations.SPLASH
    ) {
        composable(route = AuthDestinations.SPLASH) {
            SplashScreen(
                appState = appState
            )
        }

        loginGraph(
            appState = appState
        )

        signUpGraph(
            appState = appState,
            wineyBottomSheetState = wineyBottomSheetState,
        )
    }
}