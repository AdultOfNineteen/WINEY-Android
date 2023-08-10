package com.teamwiney.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.teamwiney.graphs.LOGIN_ROUTE

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
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SIGNUP_ROUTE
            )
            SignUpPhoneScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { navController.navigate(SIGNUP_AUTHENTICATION) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SIGNUP_AUTHENTICATION) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SIGNUP_ROUTE
            )
            SignUpAuthenticationScreen(
                onBack = {
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(SIGNUP_ROUTE) { inclusive = true }
                    }
                },
                onSend = { },
                onConfirm = { navController.navigate(SIGNUP_FAVORITE_TASTE) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SIGNUP_FAVORITE_TASTE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SIGNUP_ROUTE
            )
            SignUpFavoriteTasteScreen(
                onBack = {
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(SIGNUP_ROUTE) { inclusive = true }
                    }
                },
                onConfirm = {},
                onSelectionComplete = { navController.navigate(SIGNUP_COMPLETE) },
                viewModel = hiltViewModel(backStackEntry)
            )
        }

        composable(route = SIGNUP_COMPLETE) {
            val backStackEntry = rememberNavControllerBackEntry(
                entry = it,
                navController = navController,
                graph = SIGNUP_ROUTE
            )
            SignUpCompleteScreen(
                onBack = { navController.navigateUp() },
                onConfirm = { /*TODO*/ },
                viewModel = hiltViewModel(backStackEntry)
            )
        }
    }
}

@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}