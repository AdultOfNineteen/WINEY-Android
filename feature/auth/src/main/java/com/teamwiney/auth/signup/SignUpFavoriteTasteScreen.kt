package com.teamwiney.auth.signup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.auth.signup.component.SignUpFavoriteItemContainer
import com.teamwiney.auth.signup.component.bottomsheet.ReturnToLoginBottomSheet
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SignUpFavoriteTasteScreen(
    bottomSheetState: WineyBottomSheetState,
    appState: WineyAppState,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val pagerState = rememberPagerState(pageCount = { uiState.favoriteTastes.size })
    val scope = rememberCoroutineScope()

    BackHandler {
        if (bottomSheetState.bottomSheetState.isVisible) {
            bottomSheetState.hideBottomSheet()
        } else {
            if (pagerState.currentPage == 0) {
                viewModel.processEvent(SignUpContract.Event.CancelTasteSelection)
            } else {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
            }
        }
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is SignUpContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                is SignUpContract.Effect.ShowBottomSheet -> {
                    when (effect.bottomSheet) {
                        is SignUpContract.BottomSheet.ReturnToLogin -> {
                            bottomSheetState.showBottomSheet {
                                ReturnToLoginBottomSheet(
                                    onConfirm = {
                                        bottomSheetState.hideBottomSheet()
                                        appState.navigate(AuthDestinations.Login.ROUTE) {
                                            popUpTo(AuthDestinations.SignUp.ROUTE) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    onCancel = {
                                        bottomSheetState.hideBottomSheet()
                                    }
                                )
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
            TopBar(
                leadingIconOnClick = {
                    if (pagerState.currentPage == 0) {
                        viewModel.processEvent(SignUpContract.Event.CancelTasteSelection)
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    }
                }
            )
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
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (pagerState.currentPage == uiState.favoriteTastes.size - 1) {
                WButton(
                    text = "확인",
                    enabled = uiState.favoriteTastes[2].signUpFavoriteItem.find { it.isSelected } != null,
                    onClick = {
                        viewModel.processEvent(SignUpContract.Event.SetPreferences)
                    },
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }
        }
    }
}
