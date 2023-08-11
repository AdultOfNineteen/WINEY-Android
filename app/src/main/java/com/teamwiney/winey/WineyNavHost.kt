package com.teamwiney.winey

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.teamwiney.login.loginGraph
import com.teamwiney.login.navigateToLoginGraph
import com.teamwiney.signup.navigateToSignUpGraph
import com.teamwiney.signup.signUpGraph
import com.teamwiney.splash.SPLASH
import com.teamwiney.splash.splashComposable

@Composable
fun WineyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH
    ) {
        // feature 모듈 별로 네비게이션 로직이 존재하는데도 불구하고 네비게이션 경로를 담은 전역 상수 파일은 무의미하다고 생각됨
        // feature 모듈이 모둡 바인딩된 app 모듈에서 람다식으로 모듈간 경로 이동을 처리해주는 게 맞는다고 생각함
        // [변경 근거 : 모듈 간 통신 시 중재 모듈을 app 모듈로 설정] https://developer.android.com/topic/modularization/patterns?hl=ko
        // TODO : 네비게이션 로직 관리에 대한 더 좋은 아이디어가 떠오르면 바꿀 예정
        splashComposable(
            onCompleted = {
                navController.navigateToLoginGraph(
                    navOptions = navOptions {
                        NavOptionsBuilder().popUpTo(
                            route = SPLASH,
                            popUpToBuilder = { inclusive = true }
                        )
                    }
                )
            }
        )
        loginGraph(
            navigateToJoinGraph = { navController.navigateToSignUpGraph() }
        )
        signUpGraph(
            navController = navController
        )
    }
}
