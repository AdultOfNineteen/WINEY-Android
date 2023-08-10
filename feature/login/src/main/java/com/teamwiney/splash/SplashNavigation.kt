package com.teamwiney.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teamwiney.graphs.LOGIN_ROUTE

const val SPLASH = "splash"

fun NavGraphBuilder.splashComposable(
    navController: NavController
) {
    composable(route = SPLASH) {
        SplashScreen(onCompleted = {
            navController.navigate(LOGIN_ROUTE) {
                popUpTo(SPLASH) { inclusive = true }
            }
        })
    }
}