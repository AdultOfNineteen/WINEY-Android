@file:OptIn(
    ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class
)

package com.teamwiney.map

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.map.components.MapBottomSheetContent
import com.teamwiney.map.manager.manageSystemUIColor
import com.teamwiney.map.model.ShopwFilter
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    appState: WineyAppState,
    viewModel: MapViewModel = hiltViewModel()
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
    val BOTTOM_NAVIGATION_HEIGHT = 60.dp
    var filter by remember {
        mutableStateOf(ShopwFilter.ALL)
    }
    val paddingValues = WindowInsets.systemBars.asPaddingValues() // 시스템바 패딩 계산
    val screenHeight = configuration.screenHeightDp.dp

    manageSystemUIColor()

    Log.i("dlgocks1", viewModel.toString())
    LaunchedEffect(Unit) {
        viewModel.getWineShops(
            shopFilter = filter.title,
            mapPosition = MapPosition(
                latitude = cameraPositionState.position.target.latitude,
                longitude = cameraPositionState.position.target.longitude,
                leftTopLatitude = cameraPositionState.contentBounds?.northLatitude ?: 0.0,
                leftTopLongitude = cameraPositionState.contentBounds?.westLongitude ?: 0.0,
                rightBottomLatitude = cameraPositionState.contentBounds?.southLatitude ?: 0.0,
                rightBottomLongitude = cameraPositionState.contentBounds?.eastLongitude ?: 0.0,
            )
        )
    }

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
                filter = filter
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { _, _ ->
                    appState.scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
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
                            Log.i(
                                "dlgocks1 : MapPosition",
                                cameraPositionState.contentBounds?.northLatitude.toString() +
                                        cameraPositionState.contentBounds?.westLongitude.toString() +
                                        cameraPositionState.contentBounds?.southLatitude.toString() +
                                        cameraPositionState.contentBounds?.eastLongitude.toString()
                            )
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
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 24.dp, end = 24.dp, top = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                ) {
                    ShopwFilter.values().forEach {
                        Text(
                            text = it.title,
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

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(42.dp))
                        .background(WineyTheme.colors.gray_50)
                        .clickable {
                            // TODO 현위치 검색
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
