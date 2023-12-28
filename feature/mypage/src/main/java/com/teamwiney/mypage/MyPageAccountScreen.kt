package com.teamwiney.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.MyPageDestinations
import com.teamwiney.mypage.components.MyPageLogOutBottomSheet
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageAccountScreen(
    appState: WineyAppState,
    viewModel: MyPageViewModel,
    bottomSheetState: WineyBottomSheetState
) {
    val effectFlow = viewModel.effect

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
            .background(WineyTheme.colors.background_1)
    ) {
        TopBar(
            content = "WINEY 계정"
        ) {
           appState.navController.navigateUp()
        }
        HeightSpacer(height = 19.dp)

        Box(
            modifier = Modifier
                .clickable { viewModel.processEvent(MyPageContract.Event.LogOut) }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 19.dp),
                text = "로그아웃",
                style = WineyTheme.typography.bodyB1.copy(
                    color = WineyTheme.colors.gray_50
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