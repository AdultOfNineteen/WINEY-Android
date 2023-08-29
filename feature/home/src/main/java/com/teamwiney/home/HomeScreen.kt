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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.CardConfig
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.WineCard
import com.teamwiney.ui.theme.WineyTheme
import kotlin.math.absoluteValue

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .verticalScroll(rememberScrollState())
    ) {
        HomeLogo()
        HomeRecommendWine()
        HomeRecommendNewbie()
    }
}

@Composable
fun HomeRecommendNewbie() {

    val configuration = LocalConfiguration.current
    val itemWidth = configuration.screenWidthDp.dp * 0.45f

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("와인 초보를 위한")
                    withStyle(style = SpanStyle(WineyTheme.colors.main_2)) {
                        append("TIP")
                    }
                    append("!")
                },
                style = WineyTheme.typography.title2,
                color = Color.White,
            )
            IconButton(
                onClick = { },
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
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .aspectRatio(1.1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_dummy_wine),
                        contentDescription = "IMG_DUMMY_WINE",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "와인이 처음이여서\n뭘 먹어서 모르겠다면?",
                        style = WineyTheme.typography.captionB1,
                        color = Color.White,
                        modifier = Modifier
                            .padding(13.dp, 8.dp)
                            .align(Alignment.BottomStart)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        HeightSpacer(height = 20.dp)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeRecommendWine() {
    // TODO : 나중에 와인 추천 리스트는 UiState로 뺄 예정
    val cardConfigList = listOf(
        CardConfig.Red, CardConfig.White, CardConfig.Rose, CardConfig.Sparkl, CardConfig.Port, CardConfig.Etc
    )

    val pagerState = rememberPagerState(pageCount = { 6 })

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

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                beyondBoundsPageCount = 2,
                contentPadding = PaddingValues(horizontal = 24.dp),
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
                    cardConfig = cardConfigList[page],
                    name = "캄포 마리나 프리미티도 디 만두리아",
                    origin = "이탈리아",
                    varieties = "모스까델 데 알레한드리아",
                    price = "8.80"
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeLogo() {
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

        AnalysisButton { }
    }
}

@Preview
@Composable
private fun AnalysisButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = WineyTheme.colors.main_3
        ),
        contentPadding = PaddingValues(
            start = 15.dp,
            end = 12.dp,
            top = 7.dp,
            bottom = 7.dp
        ),
        shape = RoundedCornerShape(25.dp),
        elevation = ButtonDefaults.buttonElevation(7.dp)
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