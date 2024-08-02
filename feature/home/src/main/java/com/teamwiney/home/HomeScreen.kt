@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.teamwiney.analysis.component.TipCard
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.AmplitudeProvider
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.WineTip
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WineCard
import com.teamwiney.ui.components.home.HomeLogo
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    appState: WineyAppState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val wineTips = uiState.wineTips.collectAsLazyPagingItems()
    val wineTipsRefreshState = wineTips.loadState.refresh

    LaunchedEffect(wineTipsRefreshState) {
        if (wineTipsRefreshState is LoadState.Error) {
            val errorMessage = wineTipsRefreshState.error.message ?: "네트워크 오류가 발생했습니다."
            appState.showSnackbar(errorMessage)
        }
    }

    LaunchedEffect(true) {
        effectFlow.collectLatest { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is HomeContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress && uiState.isFirstScroll) {
            viewModel.setIsFirstScroll(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
    ) {
        HomeLogo(
            onClick = {
                viewModel.processEvent(HomeContract.Event.ShowAnalysis)
                AmplitudeProvider.trackEvent("analysis_button_click")
            },
            hintPopupOpen = uiState.isFirstScroll
        )
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            HomeRecommendWine(
                processEvent = viewModel::processEvent,
                recommendWines = uiState.recommendWines,
                isLoading = uiState.isLoading
            )
            HomeWineTips(
                appState = appState,
                wineTips = wineTips,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun HomeWineTips(
    appState: WineyAppState,
    wineTips: LazyPagingItems<WineTip>,
    viewModel: HomeViewModel,
) {

    val configuration = LocalConfiguration.current
    val itemWidth = configuration.screenWidthDp.dp * 0.45f

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp, end = 5.dp,
                    top = 14.dp, bottom = 14.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("와인 초보를 위한 ")
                    withStyle(style = SpanStyle(WineyTheme.colors.main_2)) {
                        append("TIP")
                    }
                    append("!")
                },
                style = WineyTheme.typography.title2,
                color = Color.White,
            )
            IconButton(
                onClick = {
                    appState.navigate(HomeDestinations.WINE_TIP)
                },
                modifier = Modifier
                    .size(48.dp)
                    .rotate(180f)
                    .align(Alignment.CenterVertically),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow_48),
                    contentDescription = "IC_ARROW_RIGHT",
                    tint = Color.White
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                count = wineTips.itemCount,
                key = wineTips.itemKey(),
                contentType = wineTips.itemContentType()
            ) { index ->
                wineTips[index]?.let {
                    TipCard(
                        modifier = Modifier.width(itemWidth),
                        title = it.title,
                        thumbnail = it.thumbnail,
                        onClick = {
                            viewModel.processEvent(HomeContract.Event.ShowTipDetail(it.url))
                        }
                    )
                }
            }
        }
        HeightSpacer(height = 20.dp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeRecommendWine(
    processEvent: (HomeContract.Event) -> Unit,
    recommendWines: List<RecommendWine>,
    isLoading: Boolean,
) {
    val configuration = LocalConfiguration.current
    val itemWidth = configuration.screenWidthDp.dp * 0.75f

    val pagerState = rememberPagerState(pageCount = { recommendWines.size })

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "오늘의 와인",
                    style = WineyTheme.typography.title1,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.mipmap.ic_home_title_wine),
                    contentDescription = "IC_HOME_TITLE_WINE",
                    modifier = Modifier.size(14.dp, 22.dp)
                )
            }
            HeightSpacer(height = 12.dp)
            Text(
                text = "매일 나의 취향에 맞는 와인을 추천해 드려요!",
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_600,
            )
            HeightSpacer(height = 28.dp)
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(WineyTheme.colors.gray_800)
            )
        } else {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val pageSize = PageSize.Fixed(pageSize = itemWidth)
                val horizontalContentPadding = (configuration.screenWidthDp.dp - itemWidth) / 2

                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    beyondBoundsPageCount = 2,
                    pageSize = pageSize,
                    contentPadding = PaddingValues(horizontal = horizontalContentPadding),
                    pageSpacing = 20.dp
                ) { page ->
                    WineCard(
                        modifier = Modifier
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                alpha = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                scaleY = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        onShowDetail = {
                            processEvent(HomeContract.Event.ShowWineCardDetail(recommendWines[page].wineId))
                        },
                        color = recommendWines[page].type,
                        name = recommendWines[page].name,
                        origin = recommendWines[page].country,
                        varieties = recommendWines[page].varietal.firstOrNull() ?: "Unknown",
                        price = "${recommendWines[page].price}"
                    )
                }
            }
        }
    }
}


