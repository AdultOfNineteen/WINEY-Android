package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.teamwiney.core.common.LoginDestinations
import com.teamwiney.core.common.SplashDestinations
import com.teamwiney.login.loginGraph
import com.teamwiney.signup.signUpGraph
import com.teamwiney.splash.splashComposable

@Composable
fun WineyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashDestinations.ROUTE
    ) {
        splashComposable(
            onCompleted = {
                navController.navigate(LoginDestinations.ROUTE) {
                    popUpTo(SplashDestinations.ROUTE) { inclusive = true }
                }
            }
        )
        loginGraph(
            navController = navController
        )
        signUpGraph(
            navController = navController
        )
    }
}
