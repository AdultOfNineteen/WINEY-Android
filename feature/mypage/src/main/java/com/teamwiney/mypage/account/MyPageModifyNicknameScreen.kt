package com.teamwiney.mypage.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.mypage.MyPageContract
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageModifyNicknameScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is MyPageContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                else -> { }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar {
            appState.navController.navigateUp()
        }
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                text = "닉네임을 변경해주세요",
                style = WineyTheme.typography.title1.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            HeightSpacer(height = 14.dp)
            Text(
                text = "언제든지 다시 바꿀 수 있어요!",
                style = WineyTheme.typography.bodyM2.copy(
                    color = WineyTheme.colors.gray_700
                )
            )
            HeightSpacer(height = 30.dp)
            Text(
                text = if (uiState.newNicknameErrorState) "최대 9자로 입력해주세요." else "닉네임",
                color = if (uiState.newNicknameErrorState) WineyTheme.colors.error else WineyTheme.colors.gray_600,
                style = if (uiState.newNicknameErrorState) WineyTheme.typography.bodyM2 else WineyTheme.typography.bodyB2
            )
            HeightSpacer(10.dp)
            WTextField(
                value = uiState.newNickname,
                onValueChanged = {
                    viewModel.updateNewNickname(it)
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp).clickable {
                            viewModel.updateNewNickname("")
                        },
                        painter = painterResource(id = R.drawable.ic_textfield_delete),
                        contentDescription = "IC_TEXT_DELETE",
                        tint = Color.Unspecified
                    )
                },
                placeholderText = "",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.modifyNickname()
                    }
                ),
                onErrorState = uiState.newNicknameErrorState
            )
            Spacer(modifier = Modifier.weight(1f))
            WButton(
                text = "변경",
                onClick = {
                    keyboardController?.hide()
                    viewModel.modifyNickname()
                },
                enabled = uiState.newNickname.isNotEmpty(),
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}