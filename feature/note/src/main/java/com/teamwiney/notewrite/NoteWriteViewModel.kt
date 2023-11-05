package com.teamwiney.notewrite

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.pagingsource.SearchWinesPagingSource
import com.teamwiney.data.repository.wine.WineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteWriteViewModel @Inject constructor(
    private val wineRepository: WineRepository
) : BaseViewModel<NoteWriteContract.State, NoteWriteContract.Event, NoteWriteContract.Effect>(
    initialState = NoteWriteContract.State()
) {

    override fun reduceState(event: NoteWriteContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteWriteContract.Event.SearchWine -> {
                    searchWines()
                }
            }
        }
    }

    fun searchWines() = viewModelScope.launch {
        updateState(
            currentState.copy(
                searchWines = Pager(
                    config = PagingConfig(
                        pageSize = 10
                    ),
                    pagingSourceFactory = {
                        SearchWinesPagingSource(
                            wineRepository = wineRepository,
                            searchKeyword = currentState.searchKeyword
                        )
                    }
                ).flow.cachedIn(viewModelScope).onStart {
                    updateState(currentState.copy(isLoading = true))
                }.onCompletion {
                    updateState(currentState.copy(isLoading = false))
                }
            )
        )
    }

    fun updateSearchKeyword(searchKeyword: String) = viewModelScope.launch {
        updateState(currentState.copy(searchKeyword = searchKeyword))
    }

}