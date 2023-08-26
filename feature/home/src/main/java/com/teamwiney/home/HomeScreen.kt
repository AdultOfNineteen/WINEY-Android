@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.teamwiney.core.design.R
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
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(vertical = 20.dp)
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
    }
}

@Composable
private fun HomeRecommendWine() {

    val pagerState = rememberPagerState(pageCount = { 5 })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
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
                    color = "RED",
                    name = "캄포 마리나 프리미티도 디 만두리아",
                    origin = "이탈리아",
                    varieties = "모스까델 데 알레한드리아",
                    price = "8.80"
                )
            }
        }
    }
}

@Composable
private fun HomeLogo() {
    Text(
        text = "WINEY",
        style = TextStyle(
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.chaviera)),
            fontWeight = FontWeight(400),
            color = WineyTheme.colors.gray_400,
        ),
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

fun calculateCenterVisibleIndex(listState: LazyListState, itemCount: Int, itemWidth: Dp, density: Density): Int {
    val totalScrollOffset = listState.firstVisibleItemScrollOffset + (listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset) / 2
    val itemWidthPx = density.run { itemWidth.toPx() }

    // Calculate the index of the item that's nearest to the estimated center position
    val centerItemIndexFloat = (totalScrollOffset + itemWidthPx / 2) / itemWidthPx
    return centerItemIndexFloat.coerceIn(0f, itemCount - 1f).toInt()
}