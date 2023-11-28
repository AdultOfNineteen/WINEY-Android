package com.teamwiney.notewrite

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
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
        val wineSmells: List<WineSmell> = listOf(
            WineSmell(
                title = "과일향",
                options = listOf(
                    WineSmellOption("과일향"),
                    WineSmellOption("베리류"),
                    WineSmellOption("레몬/라임"),
                    WineSmellOption("사과/배"),
                    WineSmellOption("복숭아/자두")
                )
            ),
            WineSmell(
                title = "내추럴",
                options = listOf(
                    WineSmellOption("꽃향"),
                    WineSmellOption("풀/나무"),
                    WineSmellOption("허브향")
                )
            ),
            WineSmell(
                title = "오크향",
                options = listOf(
                    WineSmellOption("오크향"),
                    WineSmellOption("향신로"),
                    WineSmellOption("견과류"),
                    WineSmellOption("바닐라"),
                    WineSmellOption("초콜릿")
                )
            ),
            WineSmell(
                title = "기타",
                options = listOf(
                    WineSmellOption("부싯돌"),
                    WineSmellOption("빵"),
                    WineSmellOption("고무"),
                    WineSmellOption("흙/재"),
                    WineSmellOption("약품")
                )
            ),
        ),
        val thumbX: Float = 0f,
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