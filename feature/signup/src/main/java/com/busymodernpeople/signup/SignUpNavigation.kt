package com.busymodernpeople.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val SIGNUP_ROUTE = "signUpRoute"

const val SIGNUP_PHONE = "signUpPhone"
const val SIGNUP_AUTHENTICATION = "signUpAuthentication"
const val SIGNUP_FAVORITE_TASTE = "signUpFavoriteTaste"
const val SIGNUP_COMPLETE = "signUpComplete"

fun NavController.navigateToSignUpGraph(navOptions: NavOptions? = null) {
    this.navigate(SIGNUP_ROUTE, navOptions)
}

fun NavGraphBuilder.signUpGraph(
    navController: NavController
) {
    navigation(
        route = SIGNUP_ROUTE,
        startDestination = SIGNUP_PHONE
    ) {
        composable(route = SIGNUP_PHONE) {
            SignUpPhoneScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { navController.navigate(SIGNUP_AUTHENTICATION) }
            )
        }

        composable(route = SIGNUP_AUTHENTICATION) {
            SignUpAuthenticationScreen(
                onBack = { navController.navigateUp() },
                onSend = { },
                onConfirm = { navController.navigate(SIGNUP_FAVORITE_TASTE) }
            )
        }

        composable(route = SIGNUP_FAVORITE_TASTE) {
            SignUpFavoriteTasteScreen(
                onBack = { navController.navigateUp() },
                onConfirm = {},
                onSelectionComplete = { navController.navigate(SIGNUP_COMPLETE) }
            )
        }

        composable(route = SIGNUP_COMPLETE) {
            SignUpCompleteScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { /*TODO*/ }
            )
        }
    }
}