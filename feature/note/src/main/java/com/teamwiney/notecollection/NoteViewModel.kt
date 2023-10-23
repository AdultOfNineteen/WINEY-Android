package com.teamwiney.notecollection

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.WineCountry
import com.teamwiney.data.network.model.response.WineTypeResponse
import com.teamwiney.data.pagingsource.TastingNotesPagingSource
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository
) : BaseViewModel<NoteContract.State, NoteContract.Event, NoteContract.Effect>(
    initialState = NoteContract.State()
) {
    init {
        getTastingNotes()
    }

    override fun reduceState(event: NoteContract.Event) {
        viewModelScope.launch {
            when (event) {
                is NoteContract.Event.ShowFilter -> {
                    getTastingNoteFilters()
                }

                is NoteContract.Event.ApplyFilter -> {
                    getTastingNotes()
                }
            }
        }
    }

    private fun getTastingNotes() = viewModelScope.launch {
        updateState(
            currentState.copy(
                tastingNotes = Pager(
                    config = PagingConfig(
                        pageSize = 10
                    ),
                    pagingSourceFactory = {
                        TastingNotesPagingSource(
                            tastingNoteRepository = tastingNoteRepository,
                            order = currentState.selectedSort,
                            countries = currentState.selectedCountryFilter
                                .filter { it.country != "전체" }
                                .map { it.country },
                            wineTypes = currentState.selectedTypeFilter
                                .filter { it.type != "전체" }
                                .map { convertToWineType(it.type) },
                            buyAgain = if (currentState.buyAgainSelected) 1 else 0
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

    private fun getTastingNoteFilters() = viewModelScope.launch {
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
                    postEffect(NoteContract.Effect.NavigateTo(NoteDestinations.FILTER))
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

    fun updateSelectedSort(sortType: String) = viewModelScope.launch {
        updateState(currentState.copy(selectedSort = currentState.sortItems.indexOf(sortType)))
    }

    fun removeFilter(option: String) {
        if (option == "재구매 의사") {
            updateBuyAgainSelected(currentState.buyAgainSelected)
        } else {
            val selectedType = currentState.selectedTypeFilter.firstOrNull { it.type == option }
            val selectedCountry =
                currentState.selectedCountryFilter.firstOrNull { it.country == option }

            if (selectedType != null) {
                updateSelectedTypeFilter(selectedType)
            } else if (selectedCountry != null) {
                updateSelectedCountryFilter(selectedCountry)
            }
        }
    }

    fun updateSelectedTypeFilter(filterItem: WineTypeResponse) = viewModelScope.launch {
        val updatedSelectedFilter = currentState.selectedTypeFilter.toMutableSet()

        if (filterItem in updatedSelectedFilter) {
            updatedSelectedFilter.remove(filterItem)
        } else if (filterItem.type == "전체") {
            updatedSelectedFilter.clear()
            updatedSelectedFilter.add(filterItem)
        } else {
            if (!updatedSelectedFilter.any { it.type == "전체" }) {
                updatedSelectedFilter.add(filterItem)
            }
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
        } else if (filterItem.country == "전체") {
            updatedSelectedFilter.clear()
            updatedSelectedFilter.add(filterItem)
        } else {
            if (!updatedSelectedFilter.any { it.country == "전체" }) {
                updatedSelectedFilter.add(filterItem)
            }
        }

        updateState(
            currentState.copy(
                selectedCountryFilter = updatedSelectedFilter.toList()
            )
        )
    }

    fun updateBuyAgainSelected(isSelected: Boolean) = viewModelScope.launch {
        updateState(
            currentState.copy(
                buyAgainSelected = !isSelected
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

    private fun convertToWineType(input: String): String {
        return when (input) {
            "레드" -> "RED"
            "로제" -> "ROSE"
            "화이트" -> "WHITE"
            "스파클링" -> "SPARKLING"
            "포트" -> "FORTIFIED"
            "기타" -> "OTHER"
            else -> input
        }
    }

}