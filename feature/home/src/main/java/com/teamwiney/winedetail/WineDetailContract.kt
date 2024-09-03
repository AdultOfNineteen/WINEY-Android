package com.teamwiney.winedetail

import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineSummary

class WineDetailContract {

    data class State(
        val isLoading: Boolean = false,
        val wineDetail: Wine = Wine(-1L, "", "", "", "", 0, 0, 0, 0,
            WineSummary(0.toDouble(), 0, 0, 0, 0)
        ),
        val otherNotes: List<TastingNote> = emptyList(),
        val otherNotesTotalCount: Int = 0
    ) : UiState

    sealed class Event : UiEvent

    sealed class Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect()
    }
}