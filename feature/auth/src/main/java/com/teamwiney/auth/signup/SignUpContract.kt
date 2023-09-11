package com.teamwiney.auth.signup

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiSheet
import com.teamwiney.core.common.base.UiState
import com.teamwiney.ui.signup.state.SignUpFavoriteCategoryiState
import com.teamwiney.ui.signup.state.SignUpFavoriteItemUiState

class SignUpContract {

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val phoneNumber: String = "",
        val phoneNumberErrorState: Boolean = false,
        val verifyNumber: String = "",
        val verifyNumberErrorState: Boolean = false,
        val verifyNumberErrorText: String = "인증번호",
        val isTimerRunning: Boolean = true,
        val remainingTime: Int = VERIFY_NUMBER_TIMER,
        val userId: String = "",
        val favoriteTastes: List<SignUpFavoriteCategoryiState> = mutableStateListOf(
            SignUpFavoriteCategoryiState(
                title = "평소 초콜릿을 먹을 때 나는?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "밀크 초콜릿",
                        description = "안달면 초콜릿을 왜 먹어?",
                        isSelected = false,
                        keyword = "MILK"
                    ),
                    SignUpFavoriteItemUiState(
                        title = "다크 초콜릿",
                        description = "카카오 본연의 맛이지!",
                        isSelected = false,
                        keyword = "DARK"
                    )
                )
            ),
            SignUpFavoriteCategoryiState(
                title = "내가 좋아하는 커피는?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "아메리카노",
                        description = "깔끔하고 시원한",
                        isSelected = false,
                        keyword = "AMERICANO"
                    ),
                    SignUpFavoriteItemUiState(
                        title = "진하고 풍미가득한",
                        description = "카페 라떼",
                        isSelected = false,
                        keyword = "CAFE_LATTE"
                    )
                )
            ),
            SignUpFavoriteCategoryiState(
                title = "내가 평소 즐겨먹는 과일은?",
                signUpFavoriteItem = listOf(
                    SignUpFavoriteItemUiState(
                        title = "복숭아, 자두, 망고",
                        description = "달콤한 과즙이 맴도는",
                        isSelected = false,
                        keyword = "PEACH"
                    ),
                    SignUpFavoriteItemUiState(
                        title = "상큼한 과즙으로 깔끔하게",
                        description = "파인애플, 수박, 멜론",
                        isSelected = false,
                        keyword = "PINEAPPLE"
                    )
                )
            )
        ),
    ) : UiState

    sealed class Event : UiEvent {
        object SendAuthenticationButtonClicked : Event()
        object BackToLoginButtonClicked : Event()
        object CancelTasteSelectionButtonClicked : Event()
        object SetPreferences : Event()

        object VerifyCode : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()

        object VerifyCodeSuccess : Effect()
        data class ShowBottomSheet(val bottomSheet: BottomSheet) : Effect()
    }

    sealed class BottomSheet : UiSheet {
        object SendMessage : BottomSheet()
        object ReturnToLogin : BottomSheet()
        object CancelTasteSelection : BottomSheet()
    }

    companion object {
        const val PHONE_NUMBER_LENGTH = 11
        const val VERIFY_NUMBER_LENGTH = 6
        const val VERIFY_NUMBER_TIMER = 180
    }
}