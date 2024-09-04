package com.teamwiney.notedetail.notelist

import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.TastingNote

class NoteListContract {

    data class State(
        val isLoading: Boolean = false,
        val currentPage: Int = 0,
        val isLastPage: Boolean = false,
        val tastingNotes: List<TastingNote> = emptyList(),
        val tastingNotesTotalCount: Int = 0,
    ) : UiState

    sealed class Event : UiEvent {
        object FetchNotes : Event()
        object FetchMoreNotes : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect()
    }

}