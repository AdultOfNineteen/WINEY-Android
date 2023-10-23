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
                else -> {}
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
                }

                else -> {
                    postEffect(NoteDetailContract.Effect.ShowSnackBar("노트 디테일 요청에 실패하였습니다."))
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