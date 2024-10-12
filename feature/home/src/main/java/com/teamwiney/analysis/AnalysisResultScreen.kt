@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.analysis

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
import com.teamwiney.core.common.model.WineType
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalysisResultScreen(
    appState: WineyAppState,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    val pagerState = rememberPagerState(pageCount = { 6 })

    LaunchedEffect(true) {
        viewModel.processEvent(AnalysisContract.Event.GetTasteAnalysis)
        effectFlow.collectLatest { effect ->
            when (effect) {
                is AnalysisContract.Effect.NavigateTo -> {
                    appState.navigate(effect.destination, effect.navOptions)
                }

                is AnalysisContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(effect.message)
                }

                else -> { }
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

        Spacer(modifier = Modifier.weight(4f))

        Column(
            modifier = Modifier.weight(20f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "이런 와인은 어때요?",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.gray_50,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "\"${analysisData.recommendCountry}의 ${analysisData.recommendVarietal} 품종으로 만든 ${convertTypeToSpecies(analysisData.recommendWineType)}\"",
                style = WineyTheme.typography.title2,
                color = WineyTheme.colors.main_3,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }

        VerticalPager(
            state = pagerState,
            modifier = Modifier.weight(76f),
            userScrollEnabled = true,
        ) { page ->
            val animatedProgress = remember { Animatable(0f) }

            LaunchedEffect(pagerState.currentPage) {
                animatedProgress.snapTo(0f)
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
                        wineType = WineType.typeOf(analysisData.recommendWineType),
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
                .padding(bottom = 40.dp)
                .width(73.dp)
                .height(17.dp)
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

private fun convertTypeToSpecies(type: String): String {
    return when (type) {
        "RED" -> "레드 와인"
        "ROSE" -> "로제 와인"
        "WHITE" -> "화이트 와인"
        "SPARKLING" -> "스파클링 와인"
        "FORTIFIED" -> "포트 와인"
        "OTHER" -> "기타 와인"
        else -> "기타 와인"
    }
}