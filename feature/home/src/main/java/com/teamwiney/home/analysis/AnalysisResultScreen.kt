@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home.analysis

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.home.analysis.component.AnalysisTopBar
import com.teamwiney.home.analysis.component.pagercontent.WineCountryContent
import com.teamwiney.home.analysis.component.pagercontent.WinePriceContent
import com.teamwiney.home.analysis.component.pagercontent.WineScentContent
import com.teamwiney.home.analysis.component.pagercontent.WineTasteContent
import com.teamwiney.home.analysis.component.pagercontent.WineTypeContent
import com.teamwiney.home.analysis.component.pagercontent.WineVarietyContent
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AnalysisResultScreen(
    appState: WineyAppState = rememberWineyAppState(),
) {

    val pagerState = rememberPagerState(pageCount = { 6 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        AnalysisTopBar {
            appState.navController.navigateUp()
        }
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
            text = "\"이탈리아의 프리미티보 품종으로 만든 레드 와인\"",
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
        ) {
            when (it) {
                0 -> {
                    WineTypeContent()
                }

                1 -> {
                    WineCountryContent()
                }

                2 -> {
                    WineVarietyContent()
                }

                3 -> {
                    WineTasteContent()
                }

                4 -> {
                    WineScentContent()
                }

                5 -> {
                    WinePriceContent()
                }
            }
        }

        // TODO 줄리가 바꿔줄 것
        IconButton(
            onClick = {
                if (pagerState.currentPage < 6) {
                    appState.scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 30.dp)
                .size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow_48),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .rotate(270f)
            )
        }
    }
}
