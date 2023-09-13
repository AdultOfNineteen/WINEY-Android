@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home.analysis

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WButton
import com.teamwiney.ui.home.anaylsis.AnalysisBottomContent
import com.teamwiney.ui.home.anaylsis.AnalysisStartButton
import com.teamwiney.ui.signup.SignUpTopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@Preview
@Composable
fun AnalysisScreen(
    appState: WineyAppState = rememberWineyAppState(),
    showBottomSheet: (SheetContent) -> Unit = {},
    hideBottomSheet: () -> Unit = {},
    setOnHideBottomSheet: (() -> Unit) -> Unit = {}
) {

    val pagerState = rememberPagerState(pageCount = { 2 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        // TODO : TopBar가 많이 겹치는데 Button처럼 컴포넌트화 필요
        SignUpTopBar {

        }

        VerticalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false,
        ) {
            when (it) {
                0 -> AnalysisStartContent(
                    showBottomSheet,
                    hideBottomSheet = {
                        hideBottomSheet()
                        appState.scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )

                1 -> AnalysisProgressContent {
                    appState.navigate(HomeDestinations.Analysis.RESULT)
                }
            }
        }
    }
}

@Composable
private fun AnalysisProgressContent(
    getAnalytics: () -> Unit = {}
) {
    HeightSpacer(height = 39.dp)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = WineyTheme.colors.main_3
                    )
                ) {
                    append("성경님 ")
                }
                append("의 테이스팅 노트를\n분석중이예요!")
            },
            style = WineyTheme.typography.title1.copy(
                color = WineyTheme.colors.gray_50
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.mipmap.img_splash_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        WButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 92.dp),
            text = "이버튼을 없앨 예정",
            onClick = {
                getAnalytics()
            }
        )
    }
}


@Composable
private fun AnalysisStartContent(
    showBottomSheet: (SheetContent) -> Unit = {},
    hideBottomSheet: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 28.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("나의 ")
                    withStyle(
                        style = SpanStyle(
                            color = WineyTheme.colors.main_3
                        )
                    ) {
                        append("와인 취향")
                    }
                    append(" 분석")
                },
                style = WineyTheme.typography.title1.copy(
                    color = WineyTheme.colors.gray_50
                )
            )

            HeightSpacer(height = 18.dp)

            Text(
                text = "작성한 테이스팅 노트를 기반으로 나의 와인 취향을 분석해요!",
                style = WineyTheme.typography.captionB1.copy(
                    color = WineyTheme.colors.gray_700
                )
            )
        }

        HeightSpacer(height = 32.dp)

        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.mipmap.img_analysis),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            AnalysisStartButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 92.dp)
            ) {
                showBottomSheet {
                    AnalysisBottomContent {
                        hideBottomSheet()
                    }
                }
            }
        }
    }
}

