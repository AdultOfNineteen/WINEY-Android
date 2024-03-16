package com.teamwiney.auth.signup

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations

fun NavGraphBuilder.signUpGraph(
    appState: WineyAppState,
    bottomSheetState: WineyBottomSheetState,
) {
    navigation(
        route = AuthDestinations.SignUp.ROUTE,
        startDestination = AuthDestinations.SignUp.PHONE
    ) {
        composable(route = AuthDestinations.SignUp.PHONE) {
            SignUpPhoneScreen(
                bottomSheetState = bottomSheetState,
                appState = appState,
                viewModel = hiltViewModel()
            )
        }

        composable(route = "${AuthDestinations.SignUp.AUTHENTICATION}?phoneNumber={phoneNumber}",
            arguments = listOf(
                navArgument("phoneNumber") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            val phoneNumber = entry.arguments?.getString("phoneNumber") ?: ""
            SignUpAuthenticationScreen(
                bottomSheetState = bottomSheetState,
                appState = appState,
                viewModel = hiltViewModel(),
                phoneNumber = phoneNumber
            )
        }

        composable(route = AuthDestinations.SignUp.FAVORITE_TASTE) {
            SignUpFavoriteTasteScreen(
                bottomSheetState = bottomSheetState,
                appState = appState,
                viewModel = hiltViewModel()
            )
        }

        composable(route = AuthDestinations.SignUp.COMPLETE) {
            SignUpCompleteScreen(appState = appState)
        }
    }
}