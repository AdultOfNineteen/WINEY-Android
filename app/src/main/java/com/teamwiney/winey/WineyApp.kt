package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.busymodernpeople.login.LoginScreen
import com.busymodernpeople.signup.SignUpAuthenticationScreen
import com.busymodernpeople.signup.SignUpPhoneScreen
import com.teamwiney.winey.ui.splash.SplashScreen

object WineyDestinations {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val SIGNUP_PHONE = "signUpPhone"
    const val SIGNUP_AUTHENTICATION = "signUpAuthentication"
}

@Composable
fun WineyApp() {
    val navController = rememberNavController()

    // TODO: 중첩 네비게이션 그래프로 각 feature 모듈에 나눠서 관리
    NavHost(
        navController = navController,
        startDestination = WineyDestinations.SPLASH
    ) {
        composable(route = WineyDestinations.SPLASH) {
            SplashScreen(
                onCompleted = { navController.navigate(WineyDestinations.LOGIN) }
            )
        }

        composable(route = WineyDestinations.LOGIN) {
            LoginScreen(
                onKaKaoLogin = { navController.navigate(WineyDestinations.SIGNUP_PHONE) },
                onGoogleLogin = { navController.navigate(WineyDestinations.SIGNUP_PHONE)}
            )
        }

        composable(route = WineyDestinations.SIGNUP_PHONE) {
            SignUpPhoneScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { navController.navigate(WineyDestinations.SIGNUP_AUTHENTICATION) }
            )
        }

        composable(route = WineyDestinations.SIGNUP_AUTHENTICATION) {
            SignUpAuthenticationScreen(
                onBack = { navController.navigateUp() },
                onSend = {  },
                onConfirm = { }
            )
        }
    }
}
