package com.teamwiney.notedetail

import androidx.navigation.NavOptions
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiSheet
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.TastingNoteDetail

class NoteDetailContract {
    data class State(
        val isLoading: Boolean = true,
        val noteDetail: TastingNoteDetail = TastingNoteDetail.default(),
    ) : UiState

    sealed class Event : UiEvent {
        object ShowNoteOptionBottomSheet : Event()

        object ShowNoteDeleteBottomSheet : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()

        data class ShowBottomSheet(val bottomSheet: BottomSheet) : Effect()

        object NoteDeleted : Effect()
    }

    sealed class BottomSheet : UiSheet {
        object NoteOption : BottomSheet()
        object NoteDelete : BottomSheet()
    }

}