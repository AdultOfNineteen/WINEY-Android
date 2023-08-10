package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.teamwiney.login.loginGraph
import com.teamwiney.login.navigateToLoginGraph
import com.teamwiney.signup.navigateToSignUpGraph
import com.teamwiney.signup.signUpGraph
import com.teamwiney.splash.SPLASH
import com.teamwiney.splash.splashComposable

@Composable
fun WineyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH
    ) {
        splashComposable(
            navController = navController,
        )
        loginGraph(
            navigateToJoinGraph = { navController.navigateToSignUpGraph() }
        )
        signUpGraph(
            navController = navController
        )
    }
}
