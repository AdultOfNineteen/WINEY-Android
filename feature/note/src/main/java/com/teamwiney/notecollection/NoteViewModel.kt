package com.teamwiney.notecollection

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.NoteDestinations
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.WineCountryResponse
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

    fun getTastingNotes() = viewModelScope.launch {
        getTastingNotesCount()

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
                            countries = currentState.selectedCountryFilter.map { it.country },
                            wineTypes = currentState.selectedTypeFilter.map { convertToWineType(it.type) },
                            buyAgain = if (currentState.buyAgainSelected) 1 else null
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

    private fun getTastingNotesCount() = viewModelScope.launch {
        tastingNoteRepository.getTastingNotesCount(
            order = currentState.selectedSort,
            countries = currentState.selectedCountryFilter.map { it.country },
            wineTypes = currentState.selectedTypeFilter.map { convertToWineType(it.type) },
            buyAgain = if (currentState.buyAgainSelected) 1 else null
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            tastingNotesCount = it.data.result.totalCnt
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

    private fun getTastingNoteFilters() = viewModelScope.launch {
        tastingNoteRepository.getTastingNoteFilters().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val result = it.data.result

                    val currentTypeFilter = currentState.typeFilter
                    val typeFilter = result.wineTypes
                    typeFilter.forEach { type -> currentTypeFilter.find { it.type == type.type }?.count = type.count }

                    val currentCountryFilter = currentState.countryFilter
                    val countryFilter = result.countries
                   countryFilter.forEach { country -> currentCountryFilter.find { it.country == country.country }?.count = country.count }

                    updateState(
                        currentState.copy(
                            typeFilter = currentTypeFilter,
                            countryFilter = currentCountryFilter
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
        } else {
            updatedSelectedFilter.add(filterItem)
        }

        updateState(
            currentState.copy(
                selectedTypeFilter = updatedSelectedFilter.toList()
            )
        )
    }

    fun updateSelectedCountryFilter(filterItem: WineCountryResponse) = viewModelScope.launch {
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
                selectedCountryFilter = emptyList(),
                buyAgainSelected = false
            )
        )
        getTastingNotes()
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