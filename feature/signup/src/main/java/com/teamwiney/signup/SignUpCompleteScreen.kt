package com.teamwiney.signup

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpCompleteScreen(
    onBack: () -> Unit = { },
    onConfirm: () -> Unit = { },
    viewModel: SignUpViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        SignUpTopBar {
            onBack()
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "안녕하세요\n와이니에 오신 걸 환영해요!",
                style = WineyTheme.typography.title1,
                color = WineyTheme.colors.gray_50
            )

            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "시작하기",
                onClick = {
                    // TODO : Add next button Event
                    onConfirm()
                },
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}