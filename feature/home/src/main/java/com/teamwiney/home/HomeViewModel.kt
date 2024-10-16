package com.teamwiney.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.navigation.ReusableDestinations
import com.teamwiney.core.common.util.Constants.IS_FIRST_SCROLL
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.toDomain
import com.teamwiney.data.pagingsource.WineTipsPagingSource
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.wine.WineRepository
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

        getRecommendWines()
        getWineTips()
    }

    override fun reduceState(event: HomeContract.Event) {
        viewModelScope.launch {
            when (event) {
                is HomeContract.Event.ShowWineCardDetail -> {
                    postEffect(HomeContract.Effect.NavigateTo("${HomeDestinations.WINE_DETAIL}?id=${event.cardId}"))
                }

                is HomeContract.Event.ShowMoreTips -> {
                    getWineTips()
                }

                is HomeContract.Event.ShowTipDetail -> {
                    postEffect(HomeContract.Effect.NavigateTo("${ReusableDestinations.WEB_VIEW}?url=${event.url}&title=${"와인 초보자를 위한 "}&subTitle=${"TIP"}"))
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

    private fun getRecommendWines() = viewModelScope.launch {
        wineRepository.getRecommendWines().onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    updateState(
                        currentState.copy(
                            /* 와인카드 종류 테스트
                            recommendWines = listOf(
                                RecommendWine(0L, "와인", "나라", "ROSE", listOf("품종"), 10000),
                                RecommendWine(1L, "와인", "나라", "RED", listOf("품종"), 10000),
                                RecommendWine(2L, "와인", "나라", "WHITE", listOf("품종"), 10000),
                                RecommendWine(3L, "와인", "나라", "SPARKLING", listOf("품종"), 10000),
                                RecommendWine(4L, "와인", "나라", "FORTIFIED", listOf("품종"), 10000),
                                RecommendWine(5L, "와인", "나라", "ETC", listOf("품종"), 10000)
                            )*/
                            recommendWines = it.data.result.map { wine ->
                                wine.toDomain()
                            }
                        )
                    )
                }

                is ApiResult.ApiError -> {
                    postEffect(HomeContract.Effect.ShowSnackBar(it.message))
                }

                else -> {
                    postEffect(HomeContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun getWineTips() = viewModelScope.launch {
        updateState(
            currentState.copy(
                wineTips = Pager(
                    config = PagingConfig(
                        pageSize = 10
                    ),
                    pagingSourceFactory = {
                        WineTipsPagingSource(
                            wineRepository = wineRepository
                        )
                    }
                ).flow.cachedIn(viewModelScope)
            )
        )
    }

}