package com.teamwiney.analysis

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.toDomain
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val tastingNoteRepository: TastingNoteRepository
) : BaseViewModel<AnalysisContract.State, AnalysisContract.Event, AnalysisContract.Effect>(
    initialState = AnalysisContract.State()
) {
    override fun reduceState(event: AnalysisContract.Event) {
        viewModelScope.launch {
            when (event) {
                AnalysisContract.Event.GetTasteAnalysis -> {
                    getTasteAnalysis()
                }
            }
        }
    }

    fun checkTastingNotes() = viewModelScope.launch {
        tastingNoteRepository.getCheckTastingNotes().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val result = it.data.result
                    if (result.tastingNoteExists) {
                        postEffect(AnalysisContract.Effect.ScrollToPage(1))
                    } else {
                        postEffect(
                            AnalysisContract.Effect.ShowBottomSheet(
                                AnalysisContract.BottomSheet.NoTastingNotes
                            )
                        )
                    }
                }

                is ApiResult.ApiError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun getTasteAnalysis() = viewModelScope.launch {
        tastingNoteRepository.getTasteAnalysis().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    val tasteAnalysis = it.data.result
                    updateState(currentState.copy(tasteAnalysis = tasteAnalysis.toDomain()))
                }

                is ApiResult.ApiError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

}