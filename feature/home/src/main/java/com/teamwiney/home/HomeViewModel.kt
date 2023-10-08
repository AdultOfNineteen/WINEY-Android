package com.teamwiney.home

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants.IS_FIRST_SCROLL
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.toDomain
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.home.component.state.WineCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wineRepository: WineRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(
    initialState = HomeContract.State()
) {

    init {
        val isFirstScroll = runBlocking {
            dataStoreRepository.getBooleanValue(IS_FIRST_SCROLL).first()
        }
        updateState(currentState.copy(isFirstScroll = isFirstScroll))
    }

    override fun reduceState(event: HomeContract.Event) {
        viewModelScope.launch {
            when (event) {
                is HomeContract.Event.ShowWineCardDetail -> {
                    postEffect(HomeContract.Effect.NavigateTo(HomeDestinations.WINE_DETAIL))
                }

                is HomeContract.Event.ShowMoreTips -> {

                }

                is HomeContract.Event.ShowTipDetail-> {

                }

                is HomeContract.Event.ShowAnalysis -> {
                    postEffect(HomeContract.Effect.NavigateTo(HomeDestinations.Analysis.ROUTE))
                }
            }
        }
    }

    fun setIsFirstScroll(value: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.setBooleanValue(IS_FIRST_SCROLL, value)
            updateState(currentState.copy(isFirstScroll = value))
        }
    }

    fun getRecommendWines() = viewModelScope.launch {
        wineRepository.getRecommendWines().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            recommendWines = it.data.result.map { wine ->
                                val recommendWine = wine.toDomain()

                                WineCardUiState(
                                    color = recommendWine.type,
                                    name = recommendWine.name,
                                    country = recommendWine.country,
                                    varietal = recommendWine.varietal.firstOrNull() ?: "Unknown",
                                    price = recommendWine.price
                                )
                            }
                        )
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