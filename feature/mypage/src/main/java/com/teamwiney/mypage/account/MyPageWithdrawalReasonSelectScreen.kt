package com.teamwiney.mypage.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.mypage.MyPageContract
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.mypage.components.MyPageReasonSelectBottomSheet
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.components.WRoundTextField
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageWithdrawalReasonSelectScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    LaunchedEffect(true) {
        viewModel.updateWithdrawalReason("이유를 선택해주세요.")

        effectFlow.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is MyPageContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is MyPageContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is MyPageContract.BottomSheet.SelectWithdrawalReason -> {
                            bottomSheetState.showBottomSheet {
                                MyPageReasonSelectBottomSheet(
                                    onSelect = { reason ->
                                        viewModel.updateWithdrawalReason(reason)
                                        bottomSheetState.hideBottomSheet()
                                    }
                                )
                            }
                        }

                        else -> { }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "회원 탈퇴"
        ) {
            appState.navController.navigateUp()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeightSpacer(height = 20.dp)
            Text(
                text = "정말 탈퇴하시겠어요?\n이별하기 너무 아쉬워요.",
                style = WineyTheme.typography.title2.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            HeightSpacer(height = 20.dp)
            Text(
                text = "계정을 삭제하면 작성하신 노트를 포함하여 이용한 정보들이 삭제됩니다. 게정 삭제 후 7일간 다시 가입할 수 없어요.",
                style = WineyTheme.typography.subhead.copy(
                    color = WineyTheme.colors.gray_600
                )
            )
            HeightSpacer(height = 30.dp)
            Text(
                text ="계정을 삭제하려는\n이유를 알려주세요.",
                style = WineyTheme.typography.title2.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            HeightSpacer(height = 30.dp)
            ReasonSelector(
                value = uiState.withdrawalReason
            ) {
                bottomSheetState.showBottomSheet {
                    MyPageReasonSelectBottomSheet(
                        onSelect = { reason ->
                            viewModel.updateWithdrawalReason(reason)
                            bottomSheetState.hideBottomSheet()
                        }
                    )
                }
            }
            HeightSpacer(height = 30.dp)
            if (uiState.isWithdrawalReasonDirectInput) {
                WRoundTextField(
                    value = uiState.withdrawalReasonDirectInput,
                    onValueChange = viewModel::updateWithdrawalReasonDirectInput,
                    placeholderText = "이유를 작성해주세요 :)"
                )
            }
            HeightSpacer(height = 30.dp)

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            WButton(
                text = "다음",
                onClick = { appState.navigate(MyPageDestinations.WITHDRAWAL_CONFIRM) },
                enabled = (
                    (uiState.isWithdrawalReasonDirectInput && uiState.withdrawalReasonDirectInput.isNotEmpty()) ||
                            (!uiState.isWithdrawalReasonDirectInput && uiState.withdrawalReason != "이유를 선택해주세요.")
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}

@Composable
private fun ReasonSelector(
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                style = WineyTheme.typography.bodyB2.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.rotate(-90f),
                    painter = painterResource(id = com.teamwiney.core.design.R.drawable.ic_back_arrow_48),
                    contentDescription = "IC_ARROW_DOWN",
                    tint = Color.White
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(WineyTheme.colors.gray_50)
        )
    }
}