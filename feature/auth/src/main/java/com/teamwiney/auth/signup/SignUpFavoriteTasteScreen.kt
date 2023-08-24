package com.teamwiney.auth.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.signup.BottomSheetSelectionButton
import com.teamwiney.ui.signup.SheetContent
import com.teamwiney.ui.signup.SignUpBottomSheet
import com.teamwiney.ui.signup.SignUpFavoriteItemContainer
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1F2126
)
@Composable
fun SignUpFavoriteTasteScreen(
    showBottomSheet: (SheetContent) -> Unit = { },
    hideBottomSheet: () -> Unit = { },
    navController: NavController = rememberNavController(),
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val pagerState = rememberPagerState(pageCount = { uiState.favoriteTastes.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateTo -> {
                    navController.navigate(effect.destination, effect.navOptions)
                }
                is SignUpContract.Effect.ShowSnackBar -> {
                    // TODO : 스낵바에 에러 메시지 보여주기
                }
                is SignUpContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is SignUpContract.BottomSheet.CancelTasteSelection -> {
                            showBottomSheet {
                                SignUpBottomSheet {
                                    Text(
                                        text = "진행을 중단하고 처음으로\n되돌아가시겠어요?",
                                        style = WineyTheme.typography.bodyB1,
                                        color = WineyTheme.colors.gray_200,
                                        textAlign = TextAlign.Center
                                    )
                                    HeightSpacer(height = 66.dp)
                                    BottomSheetSelectionButton(
                                        onConfirm = {
                                            hideBottomSheet()
                                            navController.navigate(AuthDestinations.Login.ROUTE) {
                                                popUpTo(AuthDestinations.SignUp.ROUTE) { inclusive = true }
                                            }
                                        },
                                        onCancel = { hideBottomSheet() }
                                    )
                                    HeightSpacer(height = 40.dp)
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box {
            SignUpTopBar {
                if (pagerState.currentPage == 0) {
                    viewModel.processEvent(SignUpContract.Event.CancelTasteSelectionButtonClicked)
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                }
            }
            Text(
                text = "${pagerState.currentPage + 1}/${uiState.favoriteTastes.size}",
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
                    uiState.favoriteTastes[it],
                    updateSignUpFavoriteItemUiState = { signUpFavoriteCategoryiState ->
                        viewModel.updateSignUpFavoriteItem(signUpFavoriteCategoryiState)
                    },
                    nextStep = {
                        if (it < uiState.favoriteTastes.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(it + 1)
                            }
                        } else {
                            viewModel.processEvent(SignUpContract.Event.TasteSelectionLastItemClicked)
                        }
                    }
                )
            }
        }
    }
}
