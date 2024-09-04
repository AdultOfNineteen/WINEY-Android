package com.teamwiney.notedetail

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository
) : BaseViewModel<NoteDetailContract.State, NoteDetailContract.Event, NoteDetailContract.Effect>(
    initialState = NoteDetailContract.State()
) {

    override fun reduceState(event: NoteDetailContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteDetailContract.Event.ShowNoteOptionBottomSheet -> {
                    postEffect(
                        NoteDetailContract.Effect.ShowBottomSheet(
                            NoteDetailContract.BottomSheet.NoteOption
                        )
                    )
                }
                is NoteDetailContract.Event.ShowNoteDeleteBottomSheet -> {
                    postEffect(
                        NoteDetailContract.Effect.ShowBottomSheet(
                            NoteDetailContract.BottomSheet.NoteDelete
                        )
                    )
                }
            }
        }
    }

    fun getNoteDetail(noteId: Int) = viewModelScope.launch {
        updateState(currentState.copy(isLoading = true))
        tastingNoteRepository.getTastingNoteDetail(noteId).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    val contents = it.data.result
                    updateState(currentState.copy(noteDetail = contents))

                    getOtherNotes(contents.wineId.toInt())
                }

                else -> {
                    postEffect(NoteDetailContract.Effect.ShowSnackBar("노트 디테일 요청에 실패하였습니다."))
                }
            }
            updateState(currentState.copy(isLoading = false))
        }
    }

    private fun getOtherNotes(wineId: Int) = viewModelScope.launch {
        updateState(currentState.copy(isLoading = true))
        tastingNoteRepository.getTastingNotes(0, 5, 0, emptyList(), emptyList(), null, wineId).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    val otherNotes = it.data.result.contents
                    updateState(
                        currentState.copy(
                            otherNotes = otherNotes,
                            otherNotesTotalCount = it.data.result.totalCnt
                        )
                    )
                }

                else -> {
                    postEffect(NoteDetailContract.Effect.ShowSnackBar("다른 노트 요청에 실패하였습니다."))
                }
            }
            updateState(currentState.copy(isLoading = false))
        }
    }

    fun deleteNote(noteId: Int) = viewModelScope.launch {
        tastingNoteRepository.deleteTastingNote(noteId).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    postEffect(NoteDetailContract.Effect.NoteDeleted)
                }

                else -> {
                    postEffect(NoteDetailContract.Effect.ShowSnackBar("삭제 요청에 실패하였습니다."))
                }
            }
        }
    }

}