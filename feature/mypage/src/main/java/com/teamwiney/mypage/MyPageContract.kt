package com.teamwiney.mypage

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.core.common.model.WineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard

class MyPageContract {

    data class State(
        val isLoading: Boolean = false,
        val currentGrade: WineGrade = WineGrade.GLASS,
        val expectedMonthGrade: WineGrade = WineGrade.GLASS,
        val noteCount: Int = 0,
        val wineGradeStandard: List<WineGradeStandard> = emptyList()
    ) : UiState

    sealed class Event : UiEvent {

    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
    }

}