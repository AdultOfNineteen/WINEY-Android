@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TasteScoreHorizontalBar
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.VerticalBarGraph
import com.teamwiney.ui.components.VerticalBarGraphData
import com.teamwiney.ui.components.WineBadge
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun WineDetailScreen(
    appState: WineyAppState = rememberWineyAppState(),
    wineId: Long = 0L,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    LaunchedEffect(true) {
        viewModel.getWineDetail(wineId)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            leadingIconOnClick = {
                appState.navController.navigateUp()
            },
            content = "와인 상세정보"
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TitleAndDescription(
                type = uiState.wineDetail.type,
                name = uiState.wineDetail.name
            )

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineOrigin(uiState.wineDetail)

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineInfo(uiState.wineDetail)

            HeightSpacer(height = 33.dp)
        }
    }
}

@Composable
private fun WineInfo(wine: Wine) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(421.dp)
    ) {
        val pageCount = 5
        val pagerState = rememberPagerState(pageCount = { pageCount })

        val tasteList = listOf(
            Triple("당도", wine.sweetness, wine.wineSummary.avgSweetness),
            Triple("산도", wine.acidity, wine.wineSummary.avgAcidity),
            Triple("바디", wine.body, wine.wineSummary.avgBody),
            Triple("탄닌", wine.tannins, wine.wineSummary.avgTannins)
        )

        HorizontalPager(state = pagerState) { page ->
            val animatedProgress = remember { Animatable(0f) }

            LaunchedEffect(pagerState.currentPage) {
                animatedProgress.snapTo(0f)
                animatedProgress.animateTo(1f, tween(durationMillis = 1000))
            }

            if (page == 0) {
                WineInfoTotalBarGraph(
                    progress = animatedProgress.value,
                    wine = wine
                )
            } else {
                WineInfoBarGraph(
                    progress = animatedProgress.value,
                    taste = tasteList[page - 1].first,
                    default = tasteList[page - 1].second,
                    similar = tasteList[page - 1].third
                )
            }
        }

        Spacer(modifier = Modifier
            .fillMaxHeight()
            .weight(1f))

        PageIndicator(pagerState = pagerState, pageCount = pageCount)
    }
}

@Composable
private fun WineInfoTotalBarGraph(
    progress: Float,
    wine: Wine
) {
    Column {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WineyTheme.colors.main_2)
                        .size(12.dp)
                )
                Text(
                    text = "취향이 비슷한 사람들이 느낀 와인의 맛",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_50
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(WineyTheme.colors.point_1)
                        .size(12.dp)
                )
                Text(
                    text = "와인의 기본 맛",
                    style = WineyTheme.typography.captionM2,
                    color = WineyTheme.colors.gray_50
                )
            }
        }

        HeightSpacer(height = 17.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TasteScoreHorizontalBar(
                progress = progress,
                label = "당도",
                peopleScore = wine.wineSummary.avgSweetness,
                defaultScore = wine.sweetness
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "산도",
                peopleScore = wine.wineSummary.avgAcidity,
                defaultScore = wine.acidity
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "바디",
                peopleScore = wine.wineSummary.avgBody,
                defaultScore = wine.body
            )

            TasteScoreHorizontalBar(
                progress = progress,
                label = "탄닌",
                peopleScore = wine.wineSummary.avgTannins,
                defaultScore = wine.tannins
            )
        }
    }
}

@Composable
private fun WineInfoBarGraph(
    progress: Float,
    taste: String,
    default: Int,
    similar: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeightSpacer(height = 42.dp)

        Text(
            modifier = Modifier,
            text = taste,
            style = WineyTheme.typography.headline.copy(color = Color.White)
        )

        HeightSpacer(height = 36.dp)

        VerticalBarGraph(
            progress = progress,
            data = listOf(
                VerticalBarGraphData(
                    label = "와인의 기본맛",
                    score = default,
                    color = WineyTheme.colors.main_2
                ),
                VerticalBarGraphData(
                    label = "취향이 비슷한 사람들이\n느낀 와인의 맛",
                    score = similar,
                    color = WineyTheme.colors.point_1
                )
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PageIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) {
                WineyTheme.colors.point_1
            } else {
                WineyTheme.colors.gray_900
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)
            )
        }
    }
}

@Composable
private fun WineOrigin(
    wine: Wine
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        WineBadge(color = wine.type)

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "national an thems",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = wine.country,
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Varieties",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = wine.varietal,
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Purchase price",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.wineSummary.avgPrice}",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }
        }
    }
}

@Composable
private fun TitleAndDescription(
    type: String,
    name: String,
) {
    HeightSpacer(height = 20.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.height(68.dp),
            text = type,
            style = WineyTheme.typography.display1,
            color = WineyTheme.colors.gray_50
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_thismooth),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
    Text(
        text = name,
        style = WineyTheme.typography.bodyB2,
        color = WineyTheme.colors.gray_500
    )
}

