package com.busymodernpeople.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SPLASH = "splash"

fun NavGraphBuilder.splashComposable(
    navigateToLoginGraph: () -> Unit
) {
    composable(route = SPLASH) {
        SplashScreen(onCompleted = { navigateToLoginGraph() })
    }
}