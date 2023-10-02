package com.teamwiney.notecollection

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(

) : BaseViewModel<NoteContract.State, NoteContract.Event, NoteContract.Effect>(
    initialState = NoteContract.State()
) {
    override fun reduceState(event: NoteContract.Event) {

    }

    fun updateSelectedSort(radioButton: String) = viewModelScope.launch {
        updateState(currentState.copy(selectedSort = radioButton))
    }

    fun updateSelectedFilter(filterItem: String) = viewModelScope.launch {
        val updatedSelectedFilter = currentState.selectedFilter.toMutableSet()

        if (filterItem in updatedSelectedFilter) {
            updatedSelectedFilter.remove(filterItem)
        } else {
            updatedSelectedFilter.add(filterItem)
        }

        updateState(
            currentState.copy(
                selectedFilter = updatedSelectedFilter.toList()
            )
        )
    }

    fun resetFilter() = viewModelScope.launch {
        updateState(
            currentState.copy(
                selectedFilter = emptyList()
            )
        )
    }

    fun updateWineList() {

    }


}