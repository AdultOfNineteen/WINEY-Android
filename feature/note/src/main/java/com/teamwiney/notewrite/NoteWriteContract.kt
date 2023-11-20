package com.teamwiney.notewrite

import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.notewrite.model.WineNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteWriteContract {

    data class State(
        val isLoading: Boolean = false,
        val searchKeyword: String = "",
        val searchWines: Flow<PagingData<SearchWine>> = flowOf(
            PagingData.from(
                emptyList(),
                LoadStates(
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                    refresh = LoadState.NotLoading(endOfPaginationReached = true),
                )
            )
        ),
        val searchWinesCount: Long = 0,
        val selectedWine: SearchWine = SearchWine.default(),
        val wineNote: WineNote = WineNote.default(),
    ) : UiState

    sealed class Event : UiEvent {
        object SearchWine : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
    }

}