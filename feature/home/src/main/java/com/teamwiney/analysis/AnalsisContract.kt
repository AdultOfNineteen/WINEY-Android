package com.teamwiney.analysis

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.TasteAnalysisResponse

class AnalysisContract {

    data class State(
        val isLoading: Boolean = false,
        val tasteAnalysisResponse: TasteAnalysisResponse = TasteAnalysisResponse()
    ) : UiState

    sealed class Event : UiEvent {
        object GetTastAnalysis : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
    }
}