package com.teamwiney.notedetail.notelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteListContract.State, NoteListContract.Event, NoteListContract.Effect>(
    initialState = NoteListContract.State()
) {
    private val wineId = savedStateHandle.get<Long>("wineId") ?: -1

    init {
        processEvent(NoteListContract.Event.FetchNotes)
    }

    override fun reduceState(event: NoteListContract.Event) {
        when (event) {
            is NoteListContract.Event.FetchNotes -> {
                getTastingNotes(wineId.toInt())
            }

            is NoteListContract.Event.FetchMoreNotes -> {
                Log.d("NoteListViewModel", "FetchMoreNotes")
                loadMoreTastingNotes(wineId.toInt())
            }

        }
    }

    private fun getTastingNotes(wineId: Int) = viewModelScope.launch {
        updateState(
            currentState.copy(
                tastingNotes = emptyList(),
                currentPage = 0,
                isLastPage = false
            )
        )

        tastingNoteRepository.getTastingNotes(
            currentState.currentPage, 20, 0, emptyList(), emptyList(), null, wineId
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collect { result ->
            updateState(currentState.copy(isLoading = false))

            when (result) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            tastingNotes = result.data.result.contents,
                            tastingNotesTotalCount = result.data.result.totalCnt,
                            currentPage = if (result.data.result.isLast) currentState.currentPage else currentState.currentPage + 1,
                            isLastPage = result.data.result.isLast
                        )
                    )
                }
                is ApiResult.ApiError -> {
                    postEffect(NoteListContract.Effect.ShowSnackBar(result.message))
                }
                is ApiResult.NetworkError -> {
                    postEffect(NoteListContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun loadMoreTastingNotes(wineId: Int) = viewModelScope.launch {
        if (currentState.isLastPage) return@launch

        tastingNoteRepository.getTastingNotes(
            currentState.currentPage, 20, 0, emptyList(), emptyList(), null, wineId
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collect { result ->
            updateState(currentState.copy(isLoading = false))
            when (result) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            tastingNotes = currentState.tastingNotes + result.data.result.contents,
                            tastingNotesTotalCount = result.data.result.totalCnt,
                            currentPage = if (result.data.result.isLast) currentState.currentPage else currentState.currentPage + 1,
                            isLastPage = result.data.result.isLast
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(NoteListContract.Effect.ShowSnackBar(result.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(NoteListContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }
}