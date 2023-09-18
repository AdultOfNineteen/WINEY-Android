package com.teamwiney.auth.signup

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.auth.signup.SignUpContract.Companion.VERIFY_NUMBER_LENGTH
import com.teamwiney.auth.signup.component.SignUpTopBar
import com.teamwiney.auth.signup.component.bottomsheet.AuthenticationFailedBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.ReturnToLoginBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.SendMessageBottomSheet
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpAuthenticationScreen(
    showBottomSheet: (SheetContent) -> Unit = { },
    hideBottomSheet: () -> Unit = { },
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is SignUpContract.Effect.ShowSnackBar -> {
                    keyboardController?.hide()
                    appState.showSnackbar(effect.message)
                }

                is SignUpContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is SignUpContract.BottomSheet.SendMessage -> {
                            showBottomSheet {
                                SendMessageBottomSheet {
                                    hideBottomSheet()
                                }
                            }
                        }

                        is SignUpContract.BottomSheet.ReturnToLogin -> {
                            showBottomSheet {
                                ReturnToLoginBottomSheet(
                                    onConfirm = {
                                        hideBottomSheet()
                                        appState.navigate(AuthDestinations.Login.ROUTE) {
                                            popUpTo(AuthDestinations.SignUp.ROUTE) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    onCancel = {
                                        hideBottomSheet()
                                    }
                                )
                            }
                        }

                        is SignUpContract.BottomSheet.AuthenticationFailed -> {
                            showBottomSheet {
                                AuthenticationFailedBottomSheet {
                                    hideBottomSheet()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.verifyNumber) {
        if ((uiState.verifyNumber.length == 6 || uiState.verifyNumber.isEmpty())) {
            viewModel.updateVerifyNumberErrorText("인증번호")
            viewModel.updateVerifyNumberErrorState(false)
        } else {
            viewModel.updateVerifyNumberErrorText("인증번호 ${VERIFY_NUMBER_LENGTH}자리를 입력해주세요")
            viewModel.updateVerifyNumberErrorState(true)
        }
    }

    LaunchedEffect(uiState.isTimerRunning) {
        while (uiState.isTimerRunning && uiState.remainingTime > 0) {
            delay(1000)
            viewModel.updateRemainingTime(uiState.remainingTime - 1)
        }
        if (uiState.isTimerRunning) viewModel.updateIsTimerRunning(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        SignUpTopBar {
            viewModel.processEvent(SignUpContract.Event.BackToLoginButtonClicked)
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "인증번호를 입력해주세요",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title1
            )
            HeightSpacer(height = 40.dp)
            Text(
                text = uiState.verifyNumberErrorText,
                color = if (uiState.verifyNumberErrorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = WineyTheme.typography.bodyB2
            )
            HeightSpacer(10.dp)
            WTextField(
                value = uiState.verifyNumber,
                onValueChanged = {
                    viewModel.updateVerifyNumber(it.filter { symbol ->
                        symbol.isDigit()
                    })
                },
                placeholderText = "인증번호를 입력해주세요",
                trailingIcon = {
                    Text(
                        text = uiState.remainingTime.toMinuteSeconds(),
                        color = WineyTheme.colors.gray_50,
                        style = WineyTheme.typography.captionM1
                    )
                },
                maxLength = VERIFY_NUMBER_LENGTH,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    if (uiState.verifyNumber.length == VERIFY_NUMBER_LENGTH) {
                        appState.navigate(AuthDestinations.SignUp.FAVORITE_TASTE)
                    }
                }),
                onErrorState = uiState.verifyNumberErrorState
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
                        keyboardController?.hide()
                        // TODO: 실제 구현에서는 ViewModel의 타이머 시작 함수를 onSend에 람다로 넘겨받음
                        viewModel.resetTimer()
                        viewModel.processEvent(SignUpContract.Event.SendAuthenticationButtonClicked)
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
                    viewModel.processEvent(SignUpContract.Event.VerifyCode)
                },
                enabled = uiState.verifyNumber.length == VERIFY_NUMBER_LENGTH,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

private fun Int.toMinuteSeconds(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60

    return if (this < 0) "-:--" else String.format("%d:%02d", minutes, remainingSeconds)
}