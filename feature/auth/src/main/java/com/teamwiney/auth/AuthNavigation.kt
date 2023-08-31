package com.teamwiney.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.auth.login.loginGraph
import com.teamwiney.auth.signup.signUpGraph
import com.teamwiney.auth.splash.SplashScreen
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.core.common.domain.common.WineyAppState
import com.teamwiney.ui.signup.SheetContent

fun NavGraphBuilder.authGraph(
    appState: WineyAppState,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit,
    setOnHideBottomSheet: (() -> Unit) -> Unit
) {
    navigation(
        route = AuthDestinations.ROUTE,
        startDestination = AuthDestinations.SPLASH
    ) {
        composable(route = AuthDestinations.SPLASH) {
            SplashScreen(
                onCompleted = {
                    appState.navigate(AuthDestinations.Login.ROUTE) {
                        popUpTo(AuthDestinations.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        loginGraph(
            appState = appState
        )

        signUpGraph(
            appState = navController,
            showBottomSheet = showBottomSheet,
            hideBottomSheet = hideBottomSheet,
            setOnHideBottomSheet = setOnHideBottomSheet
        )
    }
}