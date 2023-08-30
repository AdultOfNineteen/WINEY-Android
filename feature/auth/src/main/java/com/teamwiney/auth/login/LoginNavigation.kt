package com.teamwiney.auth.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.core.common.domain.common.WineyAppState

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
                effectFlow = viewModel.effect,
                appState = appState,
                viewModel = viewModel
            )
        }
    }
}