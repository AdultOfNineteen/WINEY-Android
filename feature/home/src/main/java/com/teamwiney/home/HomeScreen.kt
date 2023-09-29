@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.home.component.state.WineCardUiState
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HintPopUp
import com.teamwiney.ui.components.TipCard
import com.teamwiney.ui.components.WineCard
import com.teamwiney.ui.components.drawColoredShadow
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.absoluteValue

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun HomeScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    LaunchedEffect(true) {
        viewModel.getRecommendWines()
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
            processEvent = viewModel::processEvent,
            hintPopupOpen = uiState.isFirstScroll
        )
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            HomeRecommendWine(
                processEvent = viewModel::processEvent,
                recommendWines = uiState.recommendWines,
                isLoading = uiState.isLoading
            )
            HomeRecommendNewbie(appState = appState)
        }
    }
}

@Composable
fun HomeRecommendNewbie(
    appState: WineyAppState
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            (0..5).forEach { _ ->
                TipCard(
                    modifier = Modifier.width(itemWidth),
                    title = "와인이 처음이여서 뭘 마셔야할지 모르겠다면?"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        HeightSpacer(height = 20.dp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeRecommendWine(
    processEvent: (HomeContract.Event) -> Unit,
    recommendWines: List<WineCardUiState>,
    isLoading: Boolean,
) {

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
                val configuration = LocalConfiguration.current
                val pageSize = PageSize.Fixed(pageSize = 282.dp)
                val horizontalContentPadding = (configuration.screenWidthDp.dp - 282.dp) / 2

                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    beyondBoundsPageCount = 2,
                    pageSize = pageSize,
                    contentPadding = PaddingValues(horizontal = horizontalContentPadding),
                    pageSpacing = 16.dp
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
                            processEvent(HomeContract.Event.WineCardShowDetailButtonClicked(0L))
                        },
                        wineColor = recommendWines[page].wineColor,
                        name = recommendWines[page].name,
                        origin = recommendWines[page].country,
                        varieties = recommendWines[page].varietal,
                        price = "${recommendWines[page].price}"
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeLogo(
    processEvent: (HomeContract.Event) -> Unit,
    hintPopupOpen: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = "WINEY",
            style = WineyTheme.typography.display2.copy(
                color = WineyTheme.colors.gray_400
            )
        )

        Box {
            var buttonHeight by remember { mutableIntStateOf(0) }
            val density = LocalDensity.current

            AnalysisButton(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    buttonHeight = density.run { coordinates.size.height / 2 + 12.dp.roundToPx() }
                },
                onClick = {
                    processEvent(HomeContract.Event.AnalysisButtonClicked)
                }
            )
            if (hintPopupOpen) {
                HintPopUp(
                    offset = IntOffset(0, buttonHeight)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnalysisButton(
    modifier: Modifier = Modifier,
    borderColor: Color = WineyTheme.colors.main_3,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .drawColoredShadow(
                color = WineyTheme.colors.main_3,
                cornerRadius = 25.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = WineyTheme.colors.background_1
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        contentPadding = PaddingValues(
            start = 15.dp,
            end = 12.dp,
            top = 7.dp,
            bottom = 7.dp
        ),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_analysis),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(3.dp))

            Text(
                text = "분석하기",
                style = WineyTheme.typography.captionB1.copy(
                    color = WineyTheme.colors.main_3
                )
            )
        }
    }
}