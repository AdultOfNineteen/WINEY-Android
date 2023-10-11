@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.notedetail


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineSummary
import com.teamwiney.notecollection.components.NoteRadioButton
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.components.TasteScoreHorizontalBar
import com.teamwiney.ui.components.TopBar
import com.teamwiney.ui.components.WineBadge
import com.teamwiney.ui.components.detail.DetailPageIndicator
import com.teamwiney.ui.components.detail.TitleAndDescription
import com.teamwiney.ui.components.detail.WineInfoBarGraph
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun NoteDetailScreen(
    appState: WineyAppState = rememberWineyAppState(),
    wineId: Long = 0L,
    showBottomSheet: (SheetContent) -> Unit,
    hideBottomSheet: () -> Unit
) {

//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val effectFlow = viewModel.effect

    val testWine = Wine(
        wineId = 0L,
        name = "와인이름",
        country = "국가",
        type = "type",
        sweetness = 1,
        acidity = 1,
        body = 1,
        tannins = 1,
        varietal = "varietal",
        wineSummary = WineSummary(
            avgSweetness = 1,
            avgAcidity = 1,
            avgBody = 1,
            avgTannins = 1,
            avgPrice = 1.0
        )
    )
    LaunchedEffect(true) {
//        viewModel.getWineDetail(wineId)
//
//        effectFlow.collectLatest { effect ->
//            when (effect) {
//                is HomeContract.Effect.NavigateTo -> {
//                    appState.navigate(effect.destination, effect.navOptions)
//                }
//
//                is HomeContract.Effect.ShowSnackBar -> {
//                    appState.showSnackbar(effect.message)
//                }
//            }
//        }
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
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_kebab_28),
                    contentDescription = null,
                    tint = WineyTheme.colors.gray_50,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(28.dp)
                        .clickable {
                            // TODO Show bottomSheet
                        }
                )
            }
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TitleAndDescription(
                type = "ETC",
                name = "캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아 캄포 마리나 프리미티도 디 만두리아"
            )

            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )


            WineOrigin(testWine)
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Feature",
                    style = WineyTheme.typography.display2,
                    color = WineyTheme.colors.gray_50
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row {
                        Canvas(modifier = Modifier.size(41.dp)) {
                            drawCircle(
                                color = Color(0xFFEC7CA4),
                                radius = size.minDimension / 2
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 7.dp)
                                .width(1.dp)
                                .height(43.dp)
                                .background(WineyTheme.colors.gray_900)
                        )
                    }

                    LazyRow(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(listOf("레몬", "배", "제비꽃", "캐러멜", "레몬", "배", "제비꽃", "캐러멜")) {
                            NoteFeatureText(
                                name = it,
                            )
                        }
                    }
                }
            }


            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )
            HeightSpacerWithLine(
                modifier = Modifier.padding(vertical = 20.dp),
                color = WineyTheme.colors.gray_900
            )

            WineInfo(testWine)

            HeightSpacer(height = 33.dp)
        }
    }
}

@Composable
private fun NoteFeatureText(
    name: String,
) {
    Text(
        text = name,
        color = WineyTheme.colors.gray_700,
        style = WineyTheme.typography.captionB1,
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .border(
                BorderStroke(
                    1.dp, WineyTheme.colors.gray_700
                ),
                RoundedCornerShape(40.dp)
            )
            .padding(10.dp)
    )
}


// 이것도 다 Design 으로 모듈로 빼고 싶은데 Wine이라는 객체를 Desing모듈에서 불러올수가 없어요
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

        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        DetailPageIndicator(pagerState = pagerState, pageCount = pageCount)
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
