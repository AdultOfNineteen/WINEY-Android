@file:OptIn(
    ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class
)

package com.teamwiney.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.map.manager.manageSystemUIColor
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MapScreen(
    appState: WineyAppState,
) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val peekHeight by remember {
        mutableStateOf(50.dp)
    }
    manageSystemUIColor()

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetPeekHeight = peekHeight,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = WineyTheme.colors.gray_900),
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 20.dp)
                        .width(66.dp)
                        .height(6.dp)
                        .background(WineyTheme.colors.gray_800)
                        .align(Alignment.CenterHorizontally)
                )

                repeat(5) {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_dummy_wine),
                            contentDescription = "IMG_WINE",
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(110.dp, 100.dp)
                        )
                        Spacer(modifier = Modifier.width(17.dp))
                        Column(Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "모이니 와인바",
                                    style = WineyTheme.typography.headline,
                                    color = WineyTheme.colors.gray_50
                                )
                                Text(
                                    text = "와인바",
                                    style = WineyTheme.typography.captionM1,
                                    color = WineyTheme.colors.gray_500
                                )
                            }
                            HeightSpacer(height = 7.dp)
                            Text(
                                text = "서울시 마포구 신공덕동",
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_700
                            )
                            HeightSpacer(height = 14.dp)
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                listOf("양식", "프랑스", "파스타").forEach {
                                    Text(
                                        text = it,
                                        color = WineyTheme.colors.gray_500,
                                        style = WineyTheme.typography.captionM1,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(40.dp))
                                            .border(
                                                BorderStroke(
                                                    1.dp, WineyTheme.colors.gray_800
                                                ),
                                                RoundedCornerShape(40.dp)
                                            )
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }

                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookemark_baseline_24),
                            contentDescription = "IC_BOOKMARK",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    // TODO 북마크
                                },
                            tint = WineyTheme.colors.main_2
                        )
                    }
                    HeightSpacerWithLine(color = WineyTheme.colors.gray_900)
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            NaverMap(
                modifier = Modifier.fillMaxSize()
            )

            AnimatedVisibility(
                visible = true,
                modifier = Modifier.align(Alignment.BottomEnd),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .offset(
                            x = (-20).dp,
                            y = -(peekHeight + 24.dp)
                        )
                        .clip(CircleShape)
                        .background(WineyTheme.colors.gray_900)
                        .padding(13.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                        contentDescription = "IC_GPS",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(22.dp),
                        tint = WineyTheme.colors.gray_50
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .systemBarsPadding()
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 30.dp),
            ) {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                ) {
                    listOf("전체", "내 장소", "바틀샵", "와인바", "음식점").forEach {
                        Text(
                            text = it,
                            modifier = Modifier
                                .clip(RoundedCornerShape(42.dp))
                                .background(WineyTheme.colors.gray_900)
                                .padding(horizontal = 15.dp, vertical = 10.dp),
                            color = WineyTheme.colors.gray_50,
                            style = WineyTheme.typography.captionB1
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(42.dp))
                        .background(WineyTheme.colors.gray_50)
                        .padding(15.dp, 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "현 위치에서 검색",
                        style = WineyTheme.typography.captionB1,
                        color = WineyTheme.colors.main_1
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh_20),
                        contentDescription = "IC_REFERSH",
                        modifier = Modifier
                            .size(12.dp)
                            .rotate(120f),
                        tint = WineyTheme.colors.main_1
                    )
                }
            }
        }
    }
}

