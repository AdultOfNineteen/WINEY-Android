package com.teamwiney.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.teamwiney.auth.signup.SignUpContract.Companion.PHONE_NUMBER_LENGTH
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PhoneNumberVisualTransformation
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.signup.SheetContent
import com.teamwiney.ui.signup.SignUpBottomSheet
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpPhoneScreen(
    showBottomSheet: (SheetContent) -> Unit = { },
    hideBottomSheet: () -> Unit = { },
    navController: NavController = rememberNavController(),
    viewModel: SignUpViewModel = hiltViewModel(),
    onHideBottomSheet: (() -> Unit) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    DisposableEffect(true) {
        onHideBottomSheet {
            hideBottomSheet()
            navController.navigate(AuthDestinations.SignUp.AUTHENTICATION)
        }
        onDispose {
            onHideBottomSheet { }
        }
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.ShowSnackbar -> {
                    // TODO : 스낵바에 에러 메시지 보여주기
                }

                is SignUpContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is SignUpContract.BottomSheet.SendMessage -> {
                            showBottomSheet {
                                SignUpBottomSheet {
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
                                    HeightSpacer(height = 40.dp)
                                    WButton(
                                        text = "확인",
                                        onClick = {
                                            hideBottomSheet()
                                            navController.navigate(AuthDestinations.SignUp.AUTHENTICATION)
                                        }
                                    )
                                    HeightSpacer(height = 40.dp)
                                }
                            }

                        }

                        else -> {}
                    }
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(uiState.phoneNumber) {
        viewModel.updatePhoneNumberErrorState(
            !(uiState.phoneNumber.length == PHONE_NUMBER_LENGTH || uiState.phoneNumber.isEmpty())
        )
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        SignUpTopBar {
            navController.navigateUp()
        }
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = "휴대폰 번호를 입력해주세요",
                color = WineyTheme.colors.gray_50,
                style = WineyTheme.typography.title1
            )
            HeightSpacer(54.dp)
            Text(
                text = if (uiState.phoneNumberErrorState) "올바른 번호를 입력해주세요" else "전화번호",
                color = if (uiState.phoneNumberErrorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = WineyTheme.typography.bodyB2
            )
            HeightSpacer(10.dp)
            WTextField(
                value = uiState.phoneNumber,
                onValueChanged = { viewModel.updatePhoneNumber(it) },
                placeholderText = "${PHONE_NUMBER_LENGTH}자리 입력",
                maxLength = PHONE_NUMBER_LENGTH,
                visualTransformation = PhoneNumberVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.processEvent(SignUpContract.Event.SendAuthenticationCode)
                    }
                ),
                onErrorState = uiState.phoneNumberErrorState
            )
            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "확인",
                onClick = {
                    keyboardController?.hide()
                    viewModel.processEvent(SignUpContract.Event.SendAuthenticationCode)
                },
                enabled = uiState.phoneNumber.length == PHONE_NUMBER_LENGTH,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}