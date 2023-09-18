package com.teamwiney.home

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.RecommendWineResponse

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val recommendWines: RecommendWineResponse? = null,
        val tips: List<String> = emptyList(),
    ) : UiState


    sealed class Event : UiEvent {
        object AnalysisButtonClicked : Event()
        data class WineCardShowDetailButtonClicked(val cardId: Long) : Event()
        object ShowMoreTipsButtonClicked : Event()
        data class TipItemClicked(val tipId: Long) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
    }

}