@file:OptIn(ExperimentalMaterialApi::class)

package com.teamwiney.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.signup.BottomSheetSelectionButton
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.signup.SignUpBottomSheet
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpAuthenticationScreen(
    onBack: () -> Unit = { },
    onSend: () -> Unit = { },
    onConfirm: () -> Unit = { }
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
        mutableIntStateOf(180)
    }

    LaunchedEffect(authenticationNumber) {
        errorState = !(authenticationNumber.length == 6 || authenticationNumber.isEmpty())
    }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        if (isTimerRunning) isTimerRunning = false
    }
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var bottomSheetSelection by remember { mutableIntStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current

    SignUpBottomSheet(
        bottomSheetState = bottomSheetState,
        bottomsheetContent = {
            if (bottomSheetSelection == 0) {
                Text(
                    text = "인증번호가 발송되었어요\n3분 안에 인증번호를 입력해주세요",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_200,
                    textAlign = TextAlign.Center
                )
                HeightSpacer(height = 14.dp)
                Text(
                    text = "*인증번호 요청 3회 초과 시 5분 제한",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_600
                )
                HeightSpacer(height = 26.dp)
                WButton(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "확인",
                    onClick = { scope.launch { bottomSheetState.hide() } }
                )
            } else {
                Text(
                    text = "진행을 중단하고 처음으로\n되돌아가시겠어요?",
                    style = WineyTheme.typography.bodyB1,
                    color = WineyTheme.colors.gray_200,
                    textAlign = TextAlign.Center
                )
                HeightSpacer(height = 66.dp)
                BottomSheetSelectionButton(
                    onConfirm = { onBack() },
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            SignUpTopBar {
                scope.launch { bottomSheetState.show() }
                bottomSheetSelection = 1
            }
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "인증번호를 입력해주세요",
                    color = WineyTheme.colors.gray_50,
                    style = WineyTheme.typography.title1
                )
                HeightSpacer(height = 54.dp)
                Text(
                    text = if (errorState) "인증번호 6자리를 입력해주세요" else "인증번호",
                    color = if (errorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                    style = WineyTheme.typography.bodyB2
                )
                HeightSpacer(10.dp)
                WTextField(
                    value = authenticationNumber,
                    onValueChanged = {
                        authenticationNumber = it.filter { symbol ->
                            symbol.isDigit()
                        }
                    },
                    placeholderText = "인증번호를 입력해주세요",
                    trailingIcon = {
                        Text(
                            text = remainingTime.toMinuteSeconds(),
                            color = WineyTheme.colors.gray_50,
                            style = WineyTheme.typography.captionM1
                        )
                    },
                    maxLength = 6,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        if (authenticationNumber.length == 6) onConfirm()
                    }),
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
                            keyboardController?.hide()
                            // TODO: 실제 구현에서는 ViewModel의 타이머 시작 함수를 onSend에 람다로 넘겨받음
                            isTimerRunning = true
                            remainingTime = 180
                            scope.launch { bottomSheetState.show() }
                            bottomSheetSelection = 0
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
                        keyboardController?.hide()
                        onConfirm()
                    },
                    enabled = authenticationNumber.length == 6,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
    }
}

private fun Int.toMinuteSeconds(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60

    return String.format("%d:%02d", minutes, remainingSeconds)
}