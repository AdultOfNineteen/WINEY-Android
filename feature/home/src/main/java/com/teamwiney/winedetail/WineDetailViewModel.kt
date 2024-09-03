package com.teamwiney.winedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.home.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineDetailViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val tastingNoteRepository: TastingNoteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<WineDetailContract.State, WineDetailContract.Event, WineDetailContract.Effect>(
    initialState = WineDetailContract.State()
) {
    init {
        val wineId = savedStateHandle.get<Long>("wineId") ?: -1
        getWineDetail(wineId)
        getOtherNotes(wineId)
    }

    override fun reduceState(event: WineDetailContract.Event) { }

    private fun getWineDetail(wineId: Long) = viewModelScope.launch {
        wineRepository.getWineDetail(wineId).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collect {
            when (it) {
                is ApiResult.Success -> {
                    val wineDetail = it.data.result
                    updateState(currentState.copy(wineDetail = wineDetail))
                }

                is ApiResult.ApiError -> {
                    postEffect(WineDetailContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(WineDetailContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
            updateState(currentState.copy(isLoading = false))
        }
    }

    private fun getOtherNotes(wineId: Long) = viewModelScope.launch {
        updateState(currentState.copy(isLoading = true))
        tastingNoteRepository.getTastingNotes(0, 5, 0, emptyList(), emptyList(), null, wineId.toInt()).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    val notes = it.data.result.contents
                    updateState(
                        currentState.copy(
                            otherNotes = notes,
                            otherNotesTotalCount = it.data.result.totalCnt
                        )
                    )
                }

                else -> {
                    postEffect(WineDetailContract.Effect.ShowSnackBar("노트 목록 요청에 실패하였습니다."))
                }
            }
            updateState(currentState.copy(isLoading = false))
        }
    }
}