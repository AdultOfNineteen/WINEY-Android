package com.teamwiney.home

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.util.Constants.IS_FIRST_SCROLL
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.home.component.state.WineCardUiState
import com.teamwiney.ui.components.WineColor
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
                                WineCardUiState(
                                    wineColor = convertToWineColor(wine.type),
                                    name = wine.name,
                                    country = wine.country,
                                    varietal = wine.varietal.firstOrNull() ?: "Unknown",
                                    price = wine.price
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

    private fun convertToWineColor(type: String): WineColor {
        return try  {
            WineColor.valueOf(type)
        } catch (e: IllegalArgumentException) {
            WineColor.RED
        }
    }

}