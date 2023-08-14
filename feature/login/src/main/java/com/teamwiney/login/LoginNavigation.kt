package com.teamwiney.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.core.network.service.SocialType

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
            val viewModel: LoginViewModel = hiltViewModel()

            LoginScreen(
                effectFlow = viewModel.effect,
                onKaKaoLogin = { viewModel.socialLogin(socialType = SocialType.KAKAO) },
                onGoogleLogin = { viewModel.socialLogin(socialType = SocialType.GOOGLE) },
                navigateToJoin = navigateToJoinGraph
            )
        }
    }
}