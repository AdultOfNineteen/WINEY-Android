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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.map.manager.manageSystemUIColor
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    appState: WineyAppState,
) {
    val localDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val cameraPositionState = rememberCameraPositionState()
    val peekHeight by remember {
        mutableStateOf(50.dp)
    }
    // 현 위치에서 검색 까지의 Height
    var topBarHeight by remember {
        mutableStateOf(0.dp)
    }
    var filter by remember {
        mutableStateOf("전체")
    }
    val paddingValues = WindowInsets.systemBars.asPaddingValues() // 시스템바 패딩 계산
    val screenHeight = configuration.screenHeightDp.dp
    val BOTTOM_NAVIGATION_HEIGHT = 60.dp
    manageSystemUIColor(filter)

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetPeekHeight = peekHeight,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            MapBottomSheetContent(
                isExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded,
                contentHeight = screenHeight - (topBarHeight + BOTTOM_NAVIGATION_HEIGHT + paddingValues.calculateTopPadding() + paddingValues.calculateBottomPadding()),
                filter = filter // UI 인터렉션 테스트용
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
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
                        .clickable {
                            appState.scope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdate.scrollAndZoomTo(
                                        LatLng(37.413294, 127.269311), // User Position ,
                                        15.0
                                    ),
                                )
                            }
                        }
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
            ) {
                // TODO 인터렉션 수정 필요할 듯
                if (filter == "전체") {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(start = 24.dp, end = 24.dp, top = 30.dp),
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
                                    .background(if (filter == it) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900)
                                    .clickable {
                                        filter = it
                                        appState.scope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.expand()
                                        }
                                    }
                                    .padding(horizontal = 15.dp, vertical = 10.dp),
                                color = WineyTheme.colors.gray_50,
                                style = WineyTheme.typography.captionB1
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .background(Color(0xB31F2126))
                            .statusBarsPadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back_arrow_48),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        filter = "전체"
                                        appState.scope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    }
                            )
                        }
                        Text(
                            text = filter,
                            style = WineyTheme.typography.title2.copy(
                                fontWeight = FontWeight.Bold,
                                color = WineyTheme.colors.gray_50
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(42.dp))
                        .background(WineyTheme.colors.gray_50)
                        .clickable {
                            appState.showSnackbar("현 위치에서 검색")
                        }
                        .padding(15.dp, 10.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            topBarHeight =
                                with(localDensity) { layoutCoordinates.boundsInRoot().bottom.toDp() }
                        },
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

@Composable
private fun MapBottomSheetContent(
    isExpanded: Boolean,
    contentHeight: Dp,
    filter: String,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = WineyTheme.colors.gray_950),
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .width(66.dp)
                .height(6.dp)
                .background(WineyTheme.colors.gray_800)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier
                .height(contentHeight)
                .verticalScroll(rememberScrollState())
        ) {
            if (filter == "전체") {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 13.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "내 장소",
                        style = WineyTheme.typography.title2,
                        color = WineyTheme.colors.gray_50
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bookmark_fill_24),
                        tint = WineyTheme.colors.gray_50,
                        contentDescription = "IC_BOOKMARK",
                        modifier = Modifier.size(24.dp)
                    )
                }
                HeightSpacer(height = 6.dp)
                Text(
                    text = "저장 N개",
                    style = WineyTheme.typography.captionM1,
                    color = WineyTheme.colors.gray_700,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                HeightSpacerWithLine(
                    color = WineyTheme.colors.gray_900,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterHorizontally)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_note_unselected),
                        contentDescription = "IMG_NOTE",
                        modifier = Modifier.size(112.dp)
                    )
                    Text(
                        text = "아직 저장된 장소가 없어요 :(",
                        style = WineyTheme.typography.headline,
                        color = WineyTheme.colors.gray_800,
                        modifier = Modifier.padding(top = 13.dp)
                    )
                    Text(
                        text = "마음에 드는 장소를 모아봐요",
                        style = WineyTheme.typography.bodyM2,
                        color = WineyTheme.colors.gray_800,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            } else if (filter == "음식점") {
                Image(
                    painter = painterResource(id = R.drawable.img_dummy_wine),
                    contentDescription = "IMG_WINE",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.8f)
                        .padding(top = 13.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                            Text(
                                text = "주소값 마포구 신곡덩독",
                                modifier = Modifier.padding(top = 7.dp),
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_700
                            )
                            Row(modifier = Modifier.padding(top = 14.dp)) {
                                listOf("양식", "프랑스", "파스타", "파스타", "파스타").forEach {
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
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark_fill_24),
                            contentDescription = "IC_BOOKMARK",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    // TODO 북마크
                                },
                            tint = WineyTheme.colors.main_2
                        )
                    }
                    HeightSpacer(height = 20.dp)
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                contentDescription = "IC_GPS",
                                modifier = Modifier
                                    .size(19.dp),
                                tint = WineyTheme.colors.gray_50
                            )
                            Text(
                                text = "월~화 10:00 ~ 14:00 ",
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_50,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                contentDescription = "IC_GPS",
                                modifier = Modifier
                                    .size(19.dp),
                                tint = WineyTheme.colors.gray_50
                            )
                            Text(
                                text = "송파구 올림픽로 37길 2층 ",
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_50,
                                textDecoration = TextDecoration.Underline
                            )
                            Text(
                                text = "425m",
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_800
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_gps_baseline_22),
                                contentDescription = "IC_GPS",
                                modifier = Modifier
                                    .size(19.dp),
                                tint = WineyTheme.colors.gray_50
                            )
                            Text(
                                text = "000-000-0000 ",
                                style = WineyTheme.typography.captionM1,
                                color = WineyTheme.colors.gray_50,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }

                }

            } else {
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

    }
}

private fun Modifier.contentHeight(isExpanded: Boolean, peekHeight: Dp): Modifier {
    return if (isExpanded) {
        Modifier.fillMaxHeight()
    } else {
        Modifier.height(peekHeight)
    }
}
