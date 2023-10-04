@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.analysis

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.teamwiney.analysis.component.pagercontent.WineCountryContent
import com.teamwiney.analysis.component.pagercontent.WinePriceContent
import com.teamwiney.analysis.component.pagercontent.WineScentContent
import com.teamwiney.analysis.component.pagercontent.WineTasteContent
import com.teamwiney.analysis.component.pagercontent.WineTypeContent
import com.teamwiney.analysis.component.pagercontent.WineVarietyContent
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AnalysisResultScreen(
    appState: WineyAppState = rememberWineyAppState(),
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val pagerState = rememberPagerState(pageCount = { 6 })

    LaunchedEffect(true) {
        viewModel.processEvent(AnalysisContract.Event.GetTastAnalysis)
        effectFlow.collectLatest { effect ->
            when (effect) {
                is AnalysisContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is AnalysisContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }
            }
        }
    }

    val analysisData = uiState.tasteAnalysis

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(
            leadingIconOnClick = {
                appState.navController.navigateUp()
            }
        )
        HeightSpacer(height = 20.dp)
        Text(
            text = "이런 와인은 어때요?",
            style = WineyTheme.typography.title2,
            color = WineyTheme.colors.gray_50,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        HeightSpacer(height = 39.dp)
        Text(
            text = "\"${analysisData.recommendCountry}의 ${analysisData.recommendVarietal} 품종으로 만든 ${analysisData.recommendWineType}\"",
            style = WineyTheme.typography.title2,
            color = WineyTheme.colors.main_3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        VerticalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = true,
        ) { page ->
            val animatedProgress = remember { Animatable(0f) }

            LaunchedEffect(pagerState.currentPage) {
                if (pagerState.currentPage == 2) {
                    animatedProgress.snapTo(0.2f)
                } else {
                    animatedProgress.snapTo(0f)
                }
                animatedProgress.animateTo(1f, tween(durationMillis = 1000))
            }

            when (page) {
                0 -> {
                    WineTypeContent(
                        progress = animatedProgress.value,
                        types = analysisData.top3Type,
                        totalWineCount = analysisData.totalWineCnt,
                        buyAgainCount = analysisData.buyAgainCnt
                    )
                }

                1 -> {
                    WineCountryContent(
                        progress = animatedProgress.value,
                        countries = analysisData.top3Country
                    )
                }

                2 -> {
                    WineVarietyContent(
                        progress = animatedProgress.value,
                        varietals = analysisData.top3Varietal
                    )
                }

                3 -> {
                    WineTasteContent(
                        progress = animatedProgress.value,
                        tastes = analysisData.taste
                    )
                }

                4 -> {
                    WineScentContent(
                        progress = animatedProgress.value,
                        scents = analysisData.top7Smell
                    )
                }

                5 -> {
                    WinePriceContent(
                        progress = animatedProgress.value,
                        price = analysisData.avgPrice
                    )
                }
            }
        }

        IconButton(
            onClick = {
                appState.scope.launch {
                    if (pagerState.currentPage < pagerState.pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        pagerState.animateScrollToPage(0)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
                .size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_vertical_pager_arrow),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .rotate(if (pagerState.currentPage == pagerState.pageCount - 1) 180f else 0f)
            )
        }
    }
}
