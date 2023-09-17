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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.auth.signup.SignUpContract.Companion.PHONE_NUMBER_LENGTH
import com.teamwiney.auth.signup.component.SendMessageBottomSheet
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.PhoneNumberVisualTransformation
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
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
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: SignUpViewModel = hiltViewModel(),
    onHideBottomSheet: (() -> Unit) -> Unit = {},
    userId: String = "",
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    DisposableEffect(true) {
        viewModel.updateUserId(userId)
        onHideBottomSheet {
            hideBottomSheet()
            appState.navigate(AuthDestinations.SignUp.AUTHENTICATION)
        }
        onDispose {
            onHideBottomSheet { }
        }
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is SignUpContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is SignUpContract.BottomSheet.SendMessage -> {
                            showBottomSheet {
                                SendMessageBottomSheet {
                                    hideBottomSheet()
                                    appState.navigate(AuthDestinations.SignUp.AUTHENTICATION)
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
            appState.navController.navigateUp()
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
                onValueChanged = {
                    viewModel.updatePhoneNumber(it.filter { symbol ->
                        symbol.isDigit()
                    })
                },
                placeholderText = "${PHONE_NUMBER_LENGTH}자리 입력",
                maxLength = PHONE_NUMBER_LENGTH,
                visualTransformation = PhoneNumberVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.processEvent(SignUpContract.Event.SendAuthenticationButtonClicked)
                    }
                ),
                onErrorState = uiState.phoneNumberErrorState
            )
            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "확인",
                onClick = {
                    keyboardController?.hide()
                    viewModel.processEvent(SignUpContract.Event.SendAuthenticationButtonClicked)
                },
                enabled = uiState.phoneNumber.length == PHONE_NUMBER_LENGTH,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}
