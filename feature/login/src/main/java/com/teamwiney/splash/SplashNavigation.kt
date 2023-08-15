package com.teamwiney.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teamwiney.core.common.SplashDestinations

fun NavGraphBuilder.splashComposable(
    onCompleted : () -> Unit
) {
    composable(route = SplashDestinations.ROUTE) {
        SplashScreen(onCompleted = {
            onCompleted()
        })
    }
}