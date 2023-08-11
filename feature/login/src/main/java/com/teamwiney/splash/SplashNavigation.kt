package com.teamwiney.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SPLASH = "splash"

fun NavGraphBuilder.splashComposable(
    onCompleted : () -> Unit
) {
    composable(route = SPLASH) {
        SplashScreen(onCompleted = {
            onCompleted()
            //navController.navigate(LOGIN_ROUTE) {
            //    popUpTo(SPLASH) { inclusive = true }
            //}
        })
    }
}