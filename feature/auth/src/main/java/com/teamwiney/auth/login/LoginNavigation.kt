package com.teamwiney.auth.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations

fun NavGraphBuilder.loginGraph(
    appState: WineyAppState
) {
    navigation(
        route = AuthDestinations.Login.ROUTE,
        startDestination = AuthDestinations.Login.LOGIN
    ) {
        composable(route = AuthDestinations.Login.LOGIN) {
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                appState = appState,
                viewModel = viewModel
            )
        }
        
        composable(route = AuthDestinations.Login.TERMS_OF_USE) {
            LoginTermsScreen(appState = appState)
        }
    }
}