package com.teamwiney.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.auth.login.component.SplashBackground
import com.teamwiney.core.common.ConnectivityManagerNetworkMonitor
import com.teamwiney.core.common.NetworkMonitor
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.feature.auth.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.Dispatchers

@Composable
fun SignUpCompleteScreen(
    appState: WineyAppState
) {

    val onSignUpComplete = {
        appState.navigate(HomeDestinations.ROUTE) {
            popUpTo(AuthDestinations.SignUp.ROUTE) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.teamwiney.core.design.R.mipmap.img_analysis_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {

            HeightSpacer(height = 68.dp)

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "안녕하세요\n와이니에 오신 걸 환영해요!",
                    style = WineyTheme.typography.title1,
                    color = WineyTheme.colors.gray_50
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(com.teamwiney.core.design.R.mipmap.img_signup_complete),
                        contentDescription = null
                    )
                }

                WButton(
                    text = "시작하기",
                    onClick = onSignUpComplete,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }
        }
    }
}