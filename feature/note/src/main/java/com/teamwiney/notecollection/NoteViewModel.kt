package com.teamwiney.notecollection

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineType
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository
) : BaseViewModel<NoteContract.State, NoteContract.Event, NoteContract.Effect>(
    initialState = NoteContract.State()
) {
    override fun reduceState(event: NoteContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteContract.Event.ShowFilters -> {
                    getTastingNoteFilters()
                }
            }
        }
    }

    fun getTastingNoteFilters() = viewModelScope.launch {
        tastingNoteRepository.getTastingNoteFilters().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            typeFilter = it.data.result.wineTypes,
                            countryFilter = it.data.result.countries
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(NoteContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(NoteContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun updateSelectedSort(radioButton: String) = viewModelScope.launch {
        updateState(currentState.copy(selectedSort = radioButton))
    }

    fun updateSelectedTypeFilter(filterItem: WineType) = viewModelScope.launch {
        val updatedSelectedFilter = currentState.selectedTypeFilter.toMutableSet()

        if (filterItem in updatedSelectedFilter) {
            updatedSelectedFilter.remove(filterItem)
        } else {
            updatedSelectedFilter.add(filterItem)
        }

        updateState(
            currentState.copy(
                selectedTypeFilter = updatedSelectedFilter.toList()
            )
        )
    }

    fun updateSelectedCountryFilter(filterItem: WineCountry) = viewModelScope.launch {
        val updatedSelectedFilter = currentState.selectedCountryFilter.toMutableSet()

        if (filterItem in updatedSelectedFilter) {
            updatedSelectedFilter.remove(filterItem)
        } else {
            updatedSelectedFilter.add(filterItem)
        }

        updateState(
            currentState.copy(
                selectedCountryFilter = updatedSelectedFilter.toList()
            )
        )
    }
    
    fun resetFilter() = viewModelScope.launch {
        updateState(
            currentState.copy(
                selectedTypeFilter = emptyList(),
                selectedCountryFilter = emptyList()
            )
        )
    }

    fun updateWineList() {

    }


}