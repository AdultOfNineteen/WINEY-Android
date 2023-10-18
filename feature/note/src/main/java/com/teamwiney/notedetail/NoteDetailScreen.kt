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
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.common.rememberWineyAppState
import com.teamwiney.core.common.`typealias`.SheetContent
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.MyWineTaste
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteImage
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineSummary
import com.teamwiney.data.network.model.response.WineTaste
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

    val testWine = TastingNoteDetail(
        noteId = 0L,
        wineName = "캄포 마리나 프리미티도 디 만두리아",
        noteDate = "2021.09.09",
        wineType = "RED",
        region = "region",
        star = 4,
        color = "RED",
        buyAgain = true,
        varietal = "varietal",
        officialAlcohol = 0.0,
        price = 0,
        smellKeywordList = listOf("냄새1", "냄새2"),
        myWineTaste = MyWineTaste(
            sweetness = 3.0,
            acidity = 2.0,
            tannin = 3.0,
            body = 2.0,
            alcohol = 1.0,
            finish = 2.0
        ),
        defaultWineTaste = WineTaste(
            sweetness = 4.0,
            acidity = 1.0,
            tannin = 4.0,
            body = 5.0,
        ),
        memo = "메모",
        tastingNoteImage = listOf(TastingNoteImage("1", "1"), TastingNoteImage("2", "2"))
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
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

                WineSmellFeature()

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = WineyTheme.colors.gray_900
                )

                WineInfo(testWine)

                HeightSpacerWithLine(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = WineyTheme.colors.gray_900
                )
            }
            WineMemo()

            HeightSpacer(height = 33.dp)
        }
    }
}

@Composable
fun WineMemo() {

    val imgs = listOf<String>("1", "2", "3", "4")

    Column() {
        Text(
            text = "Feature",
            style = WineyTheme.typography.display2,
            color = WineyTheme.colors.gray_50,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        HeightSpacer(height = 20.dp)

        if (imgs.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
                items(imgs) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(WineyTheme.colors.gray_900)
                    )
                }
                item {
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
            HeightSpacer(height = 36.dp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        1.dp, WineyTheme.colors.main_3
                    ),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "저희 조는 지난 MODE1에서는 1인가구와 푸드테크 소비트렌드를 중심으로 시작하여 버즈리포트 및 매체 조사를 진행하였고, 키워드를 모아 1인 가구를 위한 맞춤형 영양분석 간편식 서비스에 대해 기획하였는데요, 이부분에서 저희는 ‘사람’에 대한 그리고 위한 서비스를 만든다는 점을 보완하기 위해 타겟을 조금 더 세분화하여 장기적으로 건강관리가 필요한 만성질환자",
                modifier = Modifier.padding(14.dp),
                style = WineyTheme.typography.captionM1,
                color = WineyTheme.colors.gray_50
            )
        }
    }
}

@Composable
private fun WineSmellFeature() {
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
                Box(
                    modifier = Modifier
                        .size(41.dp)
                        .blur(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .clip(CircleShape)
                            .background(
                                color = Color(0xFFEC7CA4).copy(0.5f),
                                shape = CircleShape
                            )
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


@Composable
private fun WineInfo(tastingNoteDetail: TastingNoteDetail) {

    val wine = Wine(
        wineId = -1,
        type = tastingNoteDetail.wineType,
        name = tastingNoteDetail.wineName,
        country = tastingNoteDetail.region,
        varietal = tastingNoteDetail.varietal,
        sweetness = tastingNoteDetail.myWineTaste.sweetness.toInt(),
        acidity = tastingNoteDetail.myWineTaste.acidity.toInt(),
        body = tastingNoteDetail.myWineTaste.body.toInt(),
        tannins = tastingNoteDetail.myWineTaste.tannin.toInt(),
        wineSummary = WineSummary(
            avgPrice = tastingNoteDetail.price.toDouble(),
            avgSweetness = tastingNoteDetail.defaultWineTaste.sweetness.toInt(),
            avgAcidity = tastingNoteDetail.defaultWineTaste.acidity.toInt(),
            avgBody = tastingNoteDetail.defaultWineTaste.body.toInt(),
            avgTannins = tastingNoteDetail.defaultWineTaste.tannin.toInt()
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(421.dp)
    ) {
        val pageCount = 5
        val pagerState = rememberPagerState(pageCount = { pageCount })

        val tasteList = listOf(
            Triple(
                "당도",
                tastingNoteDetail.myWineTaste.sweetness.toInt(),
                tastingNoteDetail.defaultWineTaste.sweetness.toInt()
            ),
            Triple(
                "산도",
                tastingNoteDetail.myWineTaste.acidity.toInt(),
                tastingNoteDetail.defaultWineTaste.acidity.toInt()
            ),
            Triple(
                "바디",
                tastingNoteDetail.myWineTaste.body.toInt(),
                tastingNoteDetail.defaultWineTaste.body.toInt()
            ),
            Triple(
                "탄닌",
                tastingNoteDetail.myWineTaste.tannin.toInt(),
                tastingNoteDetail.defaultWineTaste.tannin.toInt()
            )
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
    wine: TastingNoteDetail
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        WineBadge(color = wine.wineType)

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "national an thems",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = wine.region,
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
                    text = "ABV",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.officialAlcohol.toInt()}%",
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
                    text = "${wine.price}",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Vintage",
                    style = WineyTheme.typography.captionM3,
                    color = WineyTheme.colors.gray_50
                )

                Text(
                    text = "${wine.noteDate}",
                    style = WineyTheme.typography.captionB1,
                    color = WineyTheme.colors.gray_50
                )
            }
        }
    }
}
