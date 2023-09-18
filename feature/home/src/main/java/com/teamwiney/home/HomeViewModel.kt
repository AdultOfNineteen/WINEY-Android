package com.teamwiney.home

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.auth.WineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wineRepository: WineRepository
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    initialState = HomeContract.State()
) {

    override fun reduceState(event: HomeContract.Event) {
        viewModelScope.launch {
            when (event) {
                is HomeContract.Event.WineCardShowDetailButtonClicked -> {

                }

                is HomeContract.Event.ShowMoreTipsButtonClicked -> {

                }

                is HomeContract.Event.TipItemClicked-> {

                }

                is HomeContract.Event.AnalysisButtonClicked -> {

                }
            }
        }
    }

    private fun getRecommendWines() = viewModelScope.launch {
        wineRepository.getRecommendWines().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    currentState.copy(
                        recommendWines = it.data.result
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(HomeContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(HomeContract.Effect.ShowSnackBar("네트워크 에러가 발생했습니다."))
                }
            }
        }
    }

}