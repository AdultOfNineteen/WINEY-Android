package com.teamwiney.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.common.LoginDestinations
import com.teamwiney.data.network.service.SocialType

fun NavGraphBuilder.loginGraph(
    navController: NavController
) {
    navigation(
        route = LoginDestinations.ROUTE,
        startDestination = LoginDestinations.LOGIN
    ) {
        composable(route = LoginDestinations.LOGIN) {
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                effectFlow = viewModel.effect,
                onKaKaoLogin = { viewModel.socialLogin(socialType = SocialType.KAKAO) },
                onGoogleLogin = { viewModel.socialLogin(socialType = SocialType.GOOGLE) },
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}