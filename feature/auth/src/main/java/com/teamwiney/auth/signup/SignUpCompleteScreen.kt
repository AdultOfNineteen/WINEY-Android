package com.teamwiney.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

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

            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "시작하기",
                onClick = onSignUpComplete,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}