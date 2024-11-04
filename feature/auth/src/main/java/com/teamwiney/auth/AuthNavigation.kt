package com.teamwiney.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.auth.login.loginGraph
import com.teamwiney.auth.signup.signUpGraph
import com.teamwiney.auth.splash.ForceUpdateScreen
import com.teamwiney.auth.splash.SplashScreen
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations

fun NavGraphBuilder.authGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
) {
    navigation(
        route = AuthDestinations.ROUTE,
        startDestination = AuthDestinations.SPLASH
    ) {
        composable(route = AuthDestinations.SPLASH) {
            SplashScreen(
                appState = appState,
                bottomSheetState = bottomSheetState
            )
        }

        composable(
            route = "${AuthDestinations.FORCE_UPDATE}?versionName={versionName}&updateContent={updateContent}",
            arguments = listOf(
                navArgument("versionName") { type = NavType.StringType; defaultValue = "" },
                navArgument("updateContent") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val versionName = it.arguments?.getString("versionName") ?: ""
            val updateContent = it.arguments?.getString("updateContent") ?: ""

            ForceUpdateScreen(
                versionName = versionName,
                updateContent = updateContent
            )
        }

        loginGraph(appState = appState)

        signUpGraph(
            appState = appState,
            bottomSheetState = bottomSheetState
        )
    }
}