package com.teamwiney.mypage.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.core.design.R
import com.teamwiney.mypage.MyPageContract
import com.teamwiney.mypage.MyPageViewModel
import com.teamwiney.mypage.components.MyPageLogOutBottomSheet
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageAccountScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val effectFlow = viewModel.effect

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            appState.navController.navigateUp()
        }
    }

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
                        is MyPageContract.BottomSheet.LogOut -> {
                            bottomSheetState.showBottomSheet {
                                MyPageLogOutBottomSheet(
                                    onConfirm = {
                                        bottomSheetState.hideBottomSheet()
                                        viewModel.logOut()
                                    },
                                    onCancel = {
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
            .statusBarsPadding()
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "계정 설정"
        ) {
           appState.navController.navigateUp()
        }
        Box(
            modifier = Modifier
                .clickable { appState.navigate(MyPageDestinations.MODIFY_NICKNAME) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "닉네임 수정",
                    style = WineyTheme.typography.bodyM1.copy(
                        color = WineyTheme.colors.gray_400
                    )
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "IC_ARROW_RIGHT",
                    tint = Color.Unspecified
                )
            }
        }
        HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
        Box(
            modifier = Modifier
                .clickable { viewModel.processEvent(MyPageContract.Event.LogOut) }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 19.dp),
                text = "로그아웃",
                style = WineyTheme.typography.bodyM1.copy(
                    color = WineyTheme.colors.gray_400
                )
            )
        }
        HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
        Box(
            modifier = Modifier
                .clickable { appState.navigate(MyPageDestinations.WITHDRAWAL_REASON_SELECT) }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 17.dp),
                text = "회원탈퇴",
                style = WineyTheme.typography.captionM1.copy(
                    color = WineyTheme.colors.gray_700
                ),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}