package com.teamwiney.auth.signup

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.auth.signup.SignUpContract.Companion.VERIFY_NUMBER_LENGTH
import com.teamwiney.auth.signup.component.bottomsheet.AuthenticationFailedBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.AuthenticationTimeOutBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.ReturnToLoginBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.SendDisabledBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.SendMessageBottomSheet
import com.teamwiney.auth.signup.component.bottomsheet.SendMessageBottomSheetType
import com.teamwiney.auth.signup.component.bottomsheet.SendTimeExceededLimitBottomSheet
import com.teamwiney.core.common.AmplitudeEvent
import com.teamwiney.core.common.AmplitudeProvider
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignUpAuthenticationScreen(
    bottomSheetState: WineyBottomSheetState,
    appState: WineyAppState,
    viewModel: SignUpViewModel = hiltViewModel(),
    phoneNumber: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            viewModel.processEvent(SignUpContract.Event.BackToLogin)
        }
    }

    LaunchedEffect(true) {
        viewModel.updatePhoneNumber(phoneNumber)

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
                            bottomSheetState.showBottomSheet {
                                SendMessageBottomSheet(
                                    text = "인증번호가 발송되었어요\n3분 안에 인증번호를 입력해주세요",
                                    sendMessageBottomSheetType = SendMessageBottomSheetType.SEND_MESSAGE
                                ) {
                                    bottomSheetState.hideBottomSheet()
                                    appState.navigate("${AuthDestinations.SignUp.AUTHENTICATION}?phoneNumber=${uiState.phoneNumber}")
                                }
                            }
                        }

                        is SignUpContract.BottomSheet.SendTimeExceededLimit -> {
                            bottomSheetState.showBottomSheet {
                                SendTimeExceededLimitBottomSheet {
                                    bottomSheetState.hideBottomSheet()
                                    appState.navigate(AuthDestinations.Login.ROUTE) {
                                        popUpTo(AuthDestinations.SignUp.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }

                        is SignUpContract.BottomSheet.SendDisabled -> {
                            bottomSheetState.showBottomSheet {
                                SendDisabledBottomSheet {
                                    bottomSheetState.hideBottomSheet()
                                    appState.navigate(AuthDestinations.Login.ROUTE) {
                                        popUpTo(AuthDestinations.SignUp.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }

                        is SignUpContract.BottomSheet.ReturnToLogin -> {
                            bottomSheetState.showBottomSheet {
                                ReturnToLoginBottomSheet(
                                    onConfirm = {
                                        bottomSheetState.hideBottomSheet()
                                        appState.navigate(AuthDestinations.Login.ROUTE) {
                                            popUpTo(AuthDestinations.Login.ROUTE) {
                                                inclusive = true
                                            }
                                        }
                                        AmplitudeProvider.trackEvent(AmplitudeEvent.SIGNUP_FLOW_BACK_CLICK)
                                    },
                                    onCancel = {
                                        bottomSheetState.hideBottomSheet()
                                    }
                                )
                            }
                        }

                        is SignUpContract.BottomSheet.AuthenticationFailed -> {
                            bottomSheetState.showBottomSheet {
                                AuthenticationFailedBottomSheet {
                                    bottomSheetState.hideBottomSheet()
                                    appState.navigate(AuthDestinations.Login.ROUTE) {
                                        popUpTo(AuthDestinations.SignUp.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }

                        is SignUpContract.BottomSheet.AuthenticationTimeOut -> {
                            bottomSheetState.showBottomSheet {
                                AuthenticationTimeOutBottomSheet {
                                    bottomSheetState.hideBottomSheet()
                                }
                            }
                        }

                        else -> { }
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.isTimerRunning) {
        while (uiState.isTimerRunning && uiState.remainingTime > 0) {
            delay(1000)
            viewModel.updateRemainingTime(uiState.remainingTime - 1)
        }
        if (uiState.isTimerRunning) {
            viewModel.updateIsTimerRunning(false)
            viewModel.updateIsTimeOut(true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        TopBar(
            leadingIconOnClick = {
                viewModel.processEvent(SignUpContract.Event.BackToLogin)
                AmplitudeProvider.trackEvent(AmplitudeEvent.SIGNUP_FLOW_BACK_CLICK)
            }
        )
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "인증번호를 입력해주세요",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title1
            )
            HeightSpacer(height = 54.dp)
            Text(
                text = uiState.verifyNumberErrorText,
                color = if (uiState.verifyNumberErrorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = if (uiState.verifyNumberErrorState) WineyTheme.typography.bodyM2 else WineyTheme.typography.bodyB2
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
                        viewModel.processEvent(SignUpContract.Event.VerifyCode)
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
                        viewModel.resetTimer()
                        viewModel.processEvent(SignUpContract.Event.SendAuthentication)
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
                enabled = uiState.verifyNumber.length == VERIFY_NUMBER_LENGTH && !uiState.isTimeOut,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

private fun Int.toMinuteSeconds(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60

    return if (this < 0) "0:00" else String.format("%d:%02d", minutes, remainingSeconds)
}