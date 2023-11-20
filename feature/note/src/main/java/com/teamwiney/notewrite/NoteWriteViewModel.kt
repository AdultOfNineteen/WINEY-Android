package com.teamwiney.notewrite

import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.pagingsource.SearchWinesPagingSource
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.notewrite.model.SmellKeyword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
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
                    getSearchWines()
                }
            }
        }
    }


    fun updateVintage(vintage: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(vintage = vintage)))
    }

    fun updateAlcohol(alcohol: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(alcohol = alcohol)))
    }

    fun updatePrice(price: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(price = price)))
    }

    fun updateColor(color: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(color = color)))
    }

    fun updateSmellKeywordList(smellKeywordList: List<WineSmellOption>) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(smellKeywordList = smellKeywordList.mapNotNull {
            SmellKeyword.find(
                it.name
            )
        })))
    }

    fun updateUris(uris: List<Uri>) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(imgs = uris)))
    }

    fun removeUri(uri: Uri) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(imgs = currentState.wineNote.imgs.filter {
            it != uri
        })))
    }

    fun updateMemo(memo: String) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(memo = memo)))
    }

    fun updateRating(rating: Int) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(rating = rating)))
    }

    fun updateBuyAgain(buyAgain: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(buyAgain = buyAgain)))
    }

    fun getSearchWines() = viewModelScope.launch {
        getSearchWinesCount()

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

    private fun getSearchWinesCount() = viewModelScope.launch {
        wineRepository.getSearchWinesCount(
            currentState.searchKeyword
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            searchWinesCount = it.data.result.totalCnt
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(NoteWriteContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    fun updateSearchKeyword(searchKeyword: String) = viewModelScope.launch {
        updateState(currentState.copy(searchKeyword = searchKeyword))
    }

    fun updateSelectedWine(wine: SearchWine) = viewModelScope.launch {
        updateState(currentState.copy(wineNote = currentState.wineNote.copy(wineId = wine.wineId)))
        updateState(currentState.copy(selectedWine = wine))
    }

}