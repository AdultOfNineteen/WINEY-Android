package com.teamwiney.mypage

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiSheet
import com.teamwiney.core.common.base.UiState
import com.teamwiney.core.common.model.WineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard

class MyPageContract {

    data class State(
        val isLoading: Boolean = false,
        val currentGrade: WineGrade = WineGrade.GLASS,
        val expectedMonthGrade: WineGrade = WineGrade.GLASS,
        val noteCount: Int = 0,
        val wineGradeStandard: List<WineGradeStandard> = emptyList(),
        val withdrawalReason: String = "이유를 선택해주세요.",
        val isWithdrawalReasonDirectInput: Boolean = false,
        val withdrawalReasonDirectInput: String = "",
    ) : UiState

    sealed class Event : UiEvent {
        object SelectReason : Event()

        object CompleteWithdrawal : Event()

        object LogOut : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
        data class ShowBottomSheet(val bottomSheet: BottomSheet) : Effect()
    }

    sealed class BottomSheet : UiSheet {
        object LogOut : BottomSheet()
        object SelectWithdrawalReason : BottomSheet()

        object WithdrawalComplete : BottomSheet()

    }

}