package com.teamwiney.notewrite

import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.core.common.model.WineSmell
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.Wine
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
        val selectedWineInfo: Wine = Wine.default(),
        val wineNote: WineNote = WineNote.default(),
        val hintPopupOpen: Boolean = false,
        val wineSmellKeywords: List<WineSmellKeyword> = listOf(
            WineSmellKeyword(
                title = "과일향",
                options = WineSmell.values().filter { it.type == "FRUIT" }.map { WineSmellOption(it.korName, it.value) }
            ),
            WineSmellKeyword(
                title = "내추럴",
                options = WineSmell.values().filter { it.type == "NATURAL" }.map { WineSmellOption(it.korName, it.value) }
            ),
            WineSmellKeyword(
                title = "오크향",
                options = WineSmell.values().filter { it.type == "OAK" }.map { WineSmellOption(it.korName, it.value) }
            ),
            WineSmellKeyword(
                title = "기타",
                options = WineSmell.values().filter { it.type == "OTHER" }.map { WineSmellOption(it.korName, it.value) }
            )
        ),
        val thumbX: Float = 0f
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

        object NoteWriteSuccess : Effect()
    }

}