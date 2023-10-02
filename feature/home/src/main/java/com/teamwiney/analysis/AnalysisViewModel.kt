package com.teamwiney.analysis

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
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
                AnalysisContract.Event.GetTastAnalysis -> {
                    getTasteAnalysis()
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
                    updateState(currentState.copy(tasteAnalysis = tasteAnalysis))
                    Log.d("analysis", tasteAnalysis.toString())
                }

                is ApiResult.ApiError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(AnalysisContract.Effect.ShowSnackBar("네트워크 에러가 발생했습니다."))
                }
            }
        }
    }

}