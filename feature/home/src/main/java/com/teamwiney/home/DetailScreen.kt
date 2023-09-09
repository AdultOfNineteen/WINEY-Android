@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.home

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TasteScoreHorizontalBar
import com.teamwiney.ui.components.VerticalBarGraph
import com.teamwiney.ui.components.VerticalBarGraphData
import com.teamwiney.ui.components.WineBadge
import com.teamwiney.ui.components.WineColor
import com.teamwiney.ui.theme.WineyTheme

@Composable
@Preview
fun DetailScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WineyTheme.colors.background_1)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        DetailTopBar()

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TitleAndDescription()

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineOrigin()

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineInfo()

            HeightSpacer(height = 33.dp)
        }
    }
}

@Composable
private fun WineInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(421.dp)
    ) {
        val pageCount = 5
        val pagerState = rememberPagerState(pageCount = { pageCount })

        val tasteList = listOf("", "당도", "산도", "바디", "탄닌")

        HorizontalPager(state = pagerState) { page ->
            if (page == 0) {
                WineInfoTotalBarGraph()
            } else {
                WineInfoBarGraph(
                    taste = tasteList[page],
                    default = 3,
                    similar = 5
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
private fun WineInfoTotalBarGraph() {
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
                label = "당도",
                peopleScore = 4,
                defaultScore = 2
            )

            TasteScoreHorizontalBar(
                label = "산도",
                peopleScore = 1,
                defaultScore = 3
            )

            TasteScoreHorizontalBar(
                label = "바디",
                peopleScore = 5,
                defaultScore = 2
            )

            TasteScoreHorizontalBar(
                label = "탄닌",
                peopleScore = 3,
                defaultScore = 4
            )
        }
    }
}

@Composable
private fun WineInfoBarGraph(
    taste: String,
    default: Int,
    similar: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeightSpacer(height = 42.dp)

        // VerticalBarGraph의 너비가 280.dp
        Text(
            modifier = Modifier.width(280.dp),
            text = taste,
            style = WineyTheme.typography.headline.copy(color = Color.White)
        )

        HeightSpacer(height = 36.dp)

        VerticalBarGraph(
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
private fun WineOrigin() {
    Row(
        modifier = Modifier.height(148.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        WineBadge(wineColor = WineColor.Red)

        Column {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "national an thems",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "이탈리아",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement
                    .spacedBy(
                        space = 6.dp,
                        alignment = Alignment.CenterVertically
                    )
            ) {
                Text(
                    text = "Varieties",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "모스까델 데 알레한드리아",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement
                    .spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Bottom
                    )
            ) {
                Text(
                    text = "Purchase price",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "8.80",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }
        }
    }
}

@Composable
private fun TitleAndDescription() {
    HeightSpacer(height = 20.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.height(68.dp),
            text = "ETC",
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
        text = "캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아",
        style = WineyTheme.typography.bodyB2,
        color = WineyTheme.colors.gray_500
    )
}

@Preview
@Composable
private fun DetailTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart),
            onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow_48),
                contentDescription = "IC_BACK_ARROW",
                tint = WineyTheme.colors.gray_50
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "와인 상세정보",
            style = WineyTheme.typography.title2,
            color = WineyTheme.colors.gray_50
        )
    }
}

