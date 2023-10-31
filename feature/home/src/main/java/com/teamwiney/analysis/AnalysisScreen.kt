@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.analysis

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.navOptions
import com.teamwiney.analysis.component.AnalysisBottomContent
import com.teamwiney.analysis.component.AnalysisStartButton
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.WineyBottomSheetState
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnalysisScreen(
    appState: WineyAppState,
    wineyBottomSheetState: WineyBottomSheetState
) {

    val pagerState = rememberPagerState(pageCount = { 2 })

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

        VerticalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            userScrollEnabled = false,
        ) {
            when (it) {
                0 -> AnalysisStartContent(
                    wineyBottomSheetState::showBottomSheet,
                    hideBottomSheet = {
                        wineyBottomSheetState.hideBottomSheet()
                        appState.scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )

                1 -> AnalysisProgressContent {
                    appState.navigate(
                        HomeDestinations.Analysis.RESULT,
                        navOptions {
                            popUpTo(HomeDestinations.Analysis.START) {
                                inclusive = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnalysisProgressContent(
    getAnalytics: () -> Unit = {}
) {
    LaunchedEffect(true) {
        delay(1000)
        getAnalytics()
    }

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
            painter = painterResource(id = R.mipmap.img_analysis_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}


@Composable
private fun AnalysisStartContent(
    showBottomSheet: (SheetContent) -> Unit = {},
    hideBottomSheet: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeightSpacer(height = 20.dp)
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

