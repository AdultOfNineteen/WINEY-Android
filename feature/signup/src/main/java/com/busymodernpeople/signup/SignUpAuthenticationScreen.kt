package com.busymodernpeople.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.SignUpTopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay

@Composable
fun SignUpAuthenticationScreen(
    onBack: () -> Unit,
    onSend: () -> Unit,
    onConfirm: () -> Unit
) {

    var authenticationNumber by remember {
        mutableStateOf("")
    }
    var errorState by remember {
        mutableStateOf(false)
    }

    // TODO : 실제 구현에서는 isTimerRunning, remainingTime을 ViewModel에서 관리
    var isTimerRunning by remember {
        mutableStateOf(true)
    }
    var remainingTime by remember {
        mutableStateOf(180)
    }

    LaunchedEffect(authenticationNumber) {
        errorState = !(authenticationNumber.length == 6 || authenticationNumber.isEmpty())
    }
    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && remainingTime > 0) {
            delay(1000)
            remainingTime--;
        }
        if (isTimerRunning) isTimerRunning = false
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SignUpTopBar {
            onBack()
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "인증번호를 입력해주세요",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title1
            )
            HeightSpacer(height = 54.dp)
            Text(
                text = if (errorState) "인증번호 6자리를 입력해주세요." else  "인증번호",
                color = if (errorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = WineyTheme.typography.bodyB2
            )
            HeightSpacer(10.dp)
            WTextField(
                value = authenticationNumber,
                onValueChanged = { authenticationNumber = it },
                placeholderText = "",
                trailingIcon = {
                   Text(
                       text = remainingTime.toMinuteSeconds(),
                       color = WineyTheme.colors.gray_50,
                       style = WineyTheme.typography.captionM1
                   )
                },
                maxLength = 6,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onErrorState = errorState
            )
            HeightSpacer(15.dp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "인증번호가 오지 않나요?",
                    color = WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.captionM1
                )
                Text(
                    modifier = Modifier.clickable {
                        onSend()
                        // TODO: 실제 구현에서는 ViewModel의 타이머 시작 함수를 onSend에 람다로 넘겨받음
                        isTimerRunning = true
                        remainingTime = 180
                    },
                    text = "인증번호 재전송",
                    color = WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.captionB1,
                    textDecoration = TextDecoration.Underline
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "확인",
                onClick = {
                    // TODO : Add next button Event
                    onConfirm()
                },
                enabled = authenticationNumber.length == 6,
                modifier = Modifier.padding(bottom = 54.dp)
            )
        }
    }
}

private fun Int.toMinuteSeconds() : String {
    val minutes = this / 60
    val remainingSeconds = this % 60

    return String.format("%d:%02d", minutes, remainingSeconds)
}