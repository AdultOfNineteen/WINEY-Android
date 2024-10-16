package com.teamwiney.home

import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineSummary
import com.teamwiney.data.network.model.response.WineTip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeContract {

    data class State(
        val isLoading: Boolean = false,
        val isFirstScroll: Boolean = true,
        val recommendWines: List<RecommendWine> = emptyList(),
        val wineTips: Flow<PagingData<WineTip>> = flowOf(
            PagingData.from(
                emptyList(),
                LoadStates(
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                    refresh = LoadState.NotLoading(endOfPaginationReached = true),
                )
            )
        )
    ) : UiState


    sealed class Event : UiEvent {
        object ShowAnalysis : Event()
        data class ShowWineCardDetail(val cardId: Long) : Event()
        object ShowMoreTips : Event()
        data class ShowTipDetail(val url: String) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
    }

}