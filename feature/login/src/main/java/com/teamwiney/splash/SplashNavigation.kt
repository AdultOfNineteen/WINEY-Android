package com.teamwiney.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teamwiney.core.common.LoginDestinations
import com.teamwiney.core.common.SplashDestinations

fun NavGraphBuilder.splashComposable(
    navController: NavController
) {
    composable(route = SplashDestinations.ROUTE) {
        SplashScreen(onCompleted = {
            navController.navigate(LoginDestinations.ROUTE) {
                popUpTo(SplashDestinations.ROUTE) { inclusive = true }
            }
        })
    }
}