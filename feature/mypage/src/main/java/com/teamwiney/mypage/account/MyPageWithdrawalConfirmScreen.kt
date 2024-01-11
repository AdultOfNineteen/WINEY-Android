package com.teamwiney.mypage.account

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.mypage.MyPageContract
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.mypage.components.MyPageWithdrawalCompleteBottomSheet
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageWithdrawalConfirmScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val effectFlow = viewModel.effect

    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(true) {
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
                        is MyPageContract.BottomSheet.WithdrawalComplete -> {
                            bottomSheetState.showBottomSheet {
                                MyPageWithdrawalCompleteBottomSheet {
                                    activity?.finish()
                                }
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
            HeightSpacer(height = 30.dp)
            Text(
                text = "잠깐만요! 삭제하기 전에 확인해주세요.\n계정 삭제 후에는,",
                style = WineyTheme.typography.title2.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            HeightSpacer(height = 30.dp)
            Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text(
                    text = "ㆍ 회원님의 모든 정보는 삭제됩니다.",
                    style = WineyTheme.typography.subhead.copy(
                        color = WineyTheme.colors.gray_600
                    )
                )
                Text(
                    text = "ㆍ 서비스 이용 후기 등 일부 정보는 계속 남아있을 수 있습니다.",
                    style = WineyTheme.typography.subhead.copy(
                        color = WineyTheme.colors.gray_600
                    )
                )
                Text(
                    text = "ㆍ 7일 동안 재가입할 수 없습니다.",
                    style = WineyTheme.typography.subhead.copy(
                        color = WineyTheme.colors.gray_600
                    )
                )
                Text(
                    text = "ㆍ 현재 계정으로 다시 로그인할 수 없습니다.",
                    style = WineyTheme.typography.subhead.copy(
                        color = WineyTheme.colors.gray_600
                    )
                )
            }
            HeightSpacer(height = 28.dp)
            Text(
                text = buildAnnotatedString {
                    append("아래의 계정 삭제 버튼을 누르면\n모든 정보와 활동 영구히 삭제되며,\n")
                    withStyle(style = SpanStyle(WineyTheme.colors.main_3)) {
                        append("7일 동안 다시 가입할 수 없어요.")
                    }
                },
                style = WineyTheme.typography.subhead.copy(
                    color = WineyTheme.colors.gray_600
                )
            )
            HeightSpacer(height = 48.dp)
            Text(
                text = "그래도 삭제하겠습니까?",
                style = WineyTheme.typography.title2.copy(
                    color = WineyTheme.colors.gray_50
                )
            )
            HeightSpacer(height = 30.dp)
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            WButton(
                text = "계정 삭제",
                onClick = { viewModel.withdrawal() },
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }
}