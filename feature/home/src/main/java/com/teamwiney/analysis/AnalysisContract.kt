package com.teamwiney.analysis

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiSheet
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.TasteAnalysis

class AnalysisContract {

    data class State(
        val isLoading: Boolean = false,
        val nickname: String = "",
        val tasteAnalysis: TasteAnalysis = TasteAnalysis()
    ) : UiState

    sealed class Event : UiEvent {
        object GetTasteAnalysis : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
        data class ShowBottomSheet(val bottomSheet: BottomSheet) : Effect()

        data class ScrollToPage(val page: Int) : Effect()
    }

    sealed class BottomSheet : UiSheet {
        object NoTastingNotes : BottomSheet()
    }
}