package com.teamwiney.notecollection

import androidx.navigation.NavOptions
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.core.common.model.WineType
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineTypeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoteContract {

    data class State(
        val isLoading: Boolean = false,
        val tastingNotes: Flow<PagingData<TastingNote>> = flowOf(
            PagingData.from(
                // 모든 와인 데이터를 보고싶을 때 사용 지우고 싶음 지워도 돼요
//                WineType.values().map {
//                    TastingNote(
//                        id = it.ordinal.toLong(),
//                        name = "와인이름",
//                        country = "국가",
//                        starRating = 5,
//                        buyAgain = true,
//                        wineType = WineType.convertToNoteType(it.type)
//                    )
//                },
                emptyList(),
                LoadStates(
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = true),
                    refresh = LoadState.NotLoading(endOfPaginationReached = true),
                )
            )
        ),
        val sortedGroup: List<String> = listOf("최신순", "인기순"),
        val selectedSort: Int = 0,
        val buyAgain: Int = 1,
        val typeFilter: List<WineTypeResponse> = emptyList(),
        val selectedTypeFilter: List<WineTypeResponse> = emptyList(),
        val countryFilter: List<WineCountry> = emptyList(),
        val selectedCountryFilter: List<WineCountry> = emptyList(),
    ) : UiState

    sealed class Event : UiEvent {
        object ShowFilter : Event()
        object ApplyFilter : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
    }

}