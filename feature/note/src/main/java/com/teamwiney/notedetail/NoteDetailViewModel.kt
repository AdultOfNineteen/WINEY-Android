package com.teamwiney.notedetail

import androidx.lifecycle.ViewModel
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.notecollection.NoteContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository
) : BaseViewModel<NoteContract.State, NoteContract.Event, NoteContract.Effect>(
    initialState = NoteContract.State()
) {
    override fun reduceState(event: NoteContract.Event) {
        TODO("Not yet implemented")
    }

}