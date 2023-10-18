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
import com.teamwiney.notedetail.component.NoteFeatureText
import com.teamwiney.notedetail.component.WineInfo
import com.teamwiney.notedetail.component.WineInfoTotalBarGraph
import com.teamwiney.notedetail.component.WineMemo
import com.teamwiney.notedetail.component.WineOrigin
import com.teamwiney.notedetail.component.WineSmellFeature
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
