package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.busymodernpeople.login.loginGraph
import com.busymodernpeople.login.navigateToLoginGraph
import com.busymodernpeople.signup.navigateToSignUpGraph
import com.busymodernpeople.signup.signUpGraph
import com.busymodernpeople.splash.SPLASH
import com.busymodernpeople.splash.splashComposable

@Composable
fun WineyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH
    ) {
        splashComposable(
            navigateToLoginGraph = { navController.navigateToLoginGraph() }
        )
        loginGraph(
            navigateToJoinGraph = { navController.navigateToSignUpGraph() }
        )
        signUpGraph(
            navController = navController
        )
    }
}
