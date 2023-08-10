@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package com.teamwiney.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.signup.BottomSheetSelectionButton
import com.teamwiney.ui.signup.SignUpBottomSheet
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.signup.SignUpFavoriteItemContainer
import com.teamwiney.ui.signup.state.SignUpFavoriteCategoryiState
import com.teamwiney.ui.signup.state.SignUpFavoriteItemUiState
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpFavoriteTasteScreen(
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onSelectionComplete: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val favoriteTastes = remember {
        mutableStateListOf(
            SignUpFavoriteCategoryiState(
                title = "평소 초콜릿을 먹을 때 나는?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "밀크 초콜릿",
                        description = "안달면 초콜릿을 왜 먹어?",
                        isSelected = false
                    ),
                    SignUpFavoriteItemUiState(
                        title = "다크 초콜릿",
                        description = "카카오 본연의 맛이지!",
                        isSelected = false
                    )
                )
            ),
            SignUpFavoriteCategoryiState(
                title = "내가 좋아하는 커피는?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "아메리카노",
                        description = "깔끔하고 시원한",
                        isSelected = false
                    ),
                    SignUpFavoriteItemUiState(
                        title = "진하고 풍미가득한",
                        description = "카페 라떼",
                        isSelected = false
                    )
                )
            ),
            SignUpFavoriteCategoryiState(
                title = "내가 평소 즐겨먹는 과일은?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "복숭아, 자두, 망고",
                        description = "달콤한 과즙이 맴도는",
                        isSelected = false
                    ),
                    SignUpFavoriteItemUiState(
                        title = "상큼한 과즙으로 깔끔하게",
                        description = "파인애플, 수박, 멜론",
                        isSelected = false
                    )

                )
            )
        )
    }
    val pagerState = rememberPagerState(pageCount = { favoriteTastes.size })

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    SignUpBottomSheet(
        bottomSheetState = bottomSheetState,
        bottomsheetContent = {
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
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WineyTheme.colors.background_1)
                .systemBarsPadding()
                .navigationBarsPadding()
        ) {
            Box {
                SignUpTopBar {
                    if (pagerState.currentPage == 0) {
                        scope.launch { bottomSheetState.show() }
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    }
                }
                Text(
                    text = "${pagerState.currentPage + 1}/${favoriteTastes.size}",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 24.dp),
                    color = WineyTheme.colors.gray_500,
                    style = WineyTheme.typography.bodyB2
                )
            }
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "어떤 맛을 좋아하시나요?",
                    color = WineyTheme.colors.gray_50,
                    style = WineyTheme.typography.title1
                )
                HeightSpacer(14.dp)
                Text(
                    text = "좋아하실만한 와인을 추천해드릴게요!",
                    color = WineyTheme.colors.gray_700,
                    style = WineyTheme.typography.bodyM2
                )
                HeightSpacer(77.dp)

                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false,
                ) {
                    SignUpFavoriteItemContainer(
                        favoriteTastes[it],
                        updateSignUpFavoriteItemUiState = { signUpFavoriteCategoryiState ->
                            favoriteTastes[it] = signUpFavoriteCategoryiState.copy(
                                signUpFavoriteItem = signUpFavoriteCategoryiState.signUpFavoriteItem.map { signUpFavoriteItemUiState ->
                                    signUpFavoriteItemUiState
                                }
                            )
                        },
                        nextStep = {
                            if (it < favoriteTastes.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(it + 1)
                                }
                            } else {
                                // TODO 3개다 선택됐을 때 행동 추가
                                onSelectionComplete()
                            }
                        }
                    )
                }
            }
        }
    }
}

