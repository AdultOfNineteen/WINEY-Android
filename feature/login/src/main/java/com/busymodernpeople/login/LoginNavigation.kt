package com.busymodernpeople.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val LOGIN_ROUTE = "loginRoute"

const val LOGIN = "login"

fun NavController.navigateToLoginGraph(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE, navOptions)
}

fun NavGraphBuilder.loginGraph(
    navigateToJoinGraph: () -> Unit = { }
) {
    navigation(
        route = LOGIN_ROUTE,
        startDestination = LOGIN
    ) {
        composable(route = LOGIN) {
            LoginScreen(
                onKaKaoLogin = { navigateToJoinGraph() },
                onGoogleLogin = { navigateToJoinGraph() }
            )
        }
    }
}