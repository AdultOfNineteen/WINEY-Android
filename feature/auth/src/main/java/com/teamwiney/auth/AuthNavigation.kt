package com.teamwiney.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.auth.login.loginGraph
import com.teamwiney.auth.signup.signUpGraph
import com.teamwiney.auth.splash.SplashScreen
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.ui.signup.SheetContent

fun NavGraphBuilder.authGraph(
    navController: NavController,
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
                onCompleted = { navController.navigate(AuthDestinations.Login.ROUTE) }
            )
        }

        loginGraph(
            navController = navController
        )

        signUpGraph(
            navController = navController,
            showBottomSheet = showBottomSheet,
            hideBottomSheet = hideBottomSheet,
            setOnHideBottomSheet = setOnHideBottomSheet
        )
    }
}