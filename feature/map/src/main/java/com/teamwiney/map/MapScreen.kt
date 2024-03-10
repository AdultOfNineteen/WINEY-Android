@file:OptIn(
    ExperimentalNaverMapApi::class, ExperimentalMaterialApi::class, ExperimentalNaverMapApi::class
)

package com.teamwiney.map

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.OverlayImage
import com.teamwiney.core.common.WineyAppState
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.map.MapViewModel.Companion.DEFAULT_LATLNG
import com.teamwiney.map.components.GpsIcon
import com.teamwiney.map.components.MapBottomSheetContent
import com.teamwiney.map.components.WineBottomSheetOpenPopUp
import com.teamwiney.map.manager.manageBottomBarVisibility
import com.teamwiney.map.manager.manageLocationPermission
import com.teamwiney.map.model.MovingCameraWrapper
import com.teamwiney.map.model.ShopCategory
import com.teamwiney.ui.theme.WineyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal val BOTTOM_NAVIGATION_HEIGHT = 60.dp

@Composable
fun MapScreen(
    appState: WineyAppState,
    viewModel: MapViewModel = hiltViewModel()
) {
    val localDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val peekHeight by remember {
        mutableStateOf(50.dp)
    }
    // 현 위치에서 검색 까지의 Height
    var topBarHeight by remember {
        mutableStateOf(0.dp)
    }
    val paddingValues = WindowInsets.systemBars.asPaddingValues() // 시스템바 패딩 계산
    val screenHeight = configuration.screenHeightDp.dp

    val setSelectedMarker: (WineShop) -> Unit = {
        viewModel.updateSelectMarker(it, true)
        viewModel.updateMovingCameraPosition(
            MovingCameraWrapper.MOVING(
                Location("SelectedMarker").apply {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            )
        )
    }

    val onMarkerClick: (WineShop) -> Unit = {
        setSelectedMarker(it)
        appState.scope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

    val onMapClick: () -> Unit = {
        viewModel.updateSelectMarker(null, false)
        appState.scope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    val onClickGPSIcon: () -> Unit = {
        appState.scope.launch {
            appState.cameraPositionState.animate(
                update = CameraUpdate.scrollAndZoomTo(
                    uiState.userPosition,
                    15.0
                )
            )
        }
    }

    val onClickCategory: (ShopCategory) -> Unit = { category ->
        viewModel.getWineShops(
            shopFilter = category.toString(),
            cameraPositionState = appState.cameraPositionState
        )
        viewModel.updateSelectedShopCategory(category)
        viewModel.updateSelectMarker(null, false)
        appState.scope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    val onClickTopBarBackIcon: () -> Unit = {
        onClickCategory(ShopCategory.ALL)
        appState.scope.launch {
            bottomSheetScaffoldState.bottomSheetState.collapse()
        }
    }

    manageBottomBarVisibility(
        uiState = uiState,
        updateIsMapDetail = appState::updateIsMapDetail
    )
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent)

    manageLocationPermission(
        addLocationListener = { viewModel.addLocationListener() },
        showSnackbar = { appState.showSnackbar(it) },
        removeLocationListener = { viewModel.removeLocationListener() }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is MapContract.Effect.NavigateTo -> {
                    appState.navigate(it.destination, it.navOptions)
                }

                is MapContract.Effect.ShowSnackBar -> {
                    appState.showSnackbar(it.message)
                }
            }
        }
    }

    LaunchedEffect(key1 = uiState.movingCameraPosition) {
        when (val movingCameraPosition = uiState.movingCameraPosition) {
            MovingCameraWrapper.DEFAULT -> {
                // Do Nothing
            }

            is MovingCameraWrapper.MOVING -> {
                appState.cameraPositionState.animate(
                    update = CameraUpdate.scrollTo(LatLng(movingCameraPosition.location))
                )
                viewModel.updateMovingCameraPositionToDefault()
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier
            .fillMaxSize(),
        sheetPeekHeight = peekHeight,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetContent = {
            MapBottomSheetContent(
                contentHeight = screenHeight - (topBarHeight + BOTTOM_NAVIGATION_HEIGHT + paddingValues.calculateTopPadding() + paddingValues.calculateBottomPadding()),
                shopCategory = uiState.selectedShopCategory,
                wineShops = uiState.wineShops,
                selectedMarker = uiState.selectedMarkar,
                postBookmark = { wineShop ->
                    viewModel.postBookmark(wineShop)
                },
                userPosition = uiState.userPosition,
                setSelectedMarker = { wineShop ->
                    setSelectedMarker(wineShop)
                },
                bottomBarVisibility = appState.isMapDetail.value
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = appState.cameraPositionState,
                onMapClick = { _, _ ->
                    onMapClick()
                },
                onMapLoaded = {
                    viewModel.getWineShops(
                        shopFilter = uiState.selectedShopCategory.toString(),
                        cameraPositionState = appState.cameraPositionState
                    )
                    if (MapViewModel.initialMarkerLoadFlag && uiState.userPosition != DEFAULT_LATLNG) {
                        MapViewModel.initialMarkerLoadFlag = false
                        viewModel.updateMovingCameraPosition(
                            MovingCameraWrapper.MOVING(
                                Location("UserPosition").apply {
                                    latitude = uiState.userPosition.latitude
                                    longitude = uiState.userPosition.longitude
                                })
                        )
                    }
                },
                uiSettings = MapUiSettings(
                    logoMargin = PaddingValues(
                        start = 12.dp,
                        bottom = peekHeight + 10.dp
                    )
                )
            ) {

                (if (uiState.selectedShopCategory == ShopCategory.LIKE)
                    uiState.wineShops.filter { it.like }
                else uiState.wineShops).forEach {
                    Marker(
                        state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                        icon = OverlayImage.fromResource(R.mipmap.img_wine_marker),
                        captionText = it.name,
                        height = if (it.isSelected) 75.dp else 49.dp,
                        width = if (it.isSelected) 52.dp else 34.dp,
                        onClick = { _ ->
                            onMarkerClick(it)
                            true
                        }
                    )
                }

                if (uiState.userPosition != DEFAULT_LATLNG) {
                    Marker(
                        state = MarkerState(position = uiState.userPosition),
                        icon = OverlayImage.fromResource(R.drawable.ic_map_user_24),
                        height = 24.dp,
                        width = 24.dp,
                        onClick = {
                            true
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0x737D7D7D),
                                Color.Transparent
                            )
                        )
                    )
            )

            WineBottomSheetOpenPopUp(
                isCollapsed = bottomSheetScaffoldState.bottomSheetState.isCollapsed,
                expandBottomSheet = {
                    appState.scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                },
                peekHeight = peekHeight,
            )

            GpsIcon(
                isCollapsed = bottomSheetScaffoldState.bottomSheetState.isCollapsed,
                peekHeight = peekHeight,
                onClickGPSIcon = onClickGPSIcon
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            ) {
                // 디테일 화면일 때
                if (uiState.selectedMarkar != null) {
                    WineCategoryTopbar(
                        onClickTopBarBackIcon = {
                            viewModel.updateSelectedShopCategory(uiState.selectedShopCategory)
                            viewModel.updateSelectMarker(null, false)
                            appState.scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        },
                        title = uiState.selectedMarkar!!.name
                    )
                } else {
                    // TODO 수정 필요
                    if (uiState.selectedShopCategory == ShopCategory.ALL) {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(start = 24.dp, end = 24.dp, top = 50.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                10.dp,
                                Alignment.CenterHorizontally
                            ),
                        ) {
                            ShopCategory.values().forEach {
                                Text(
                                    text = it.title,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(42.dp))
                                        .background(if (uiState.selectedShopCategory == it) WineyTheme.colors.main_2 else WineyTheme.colors.gray_900)
                                        .clickable {
                                            onClickCategory(it)
                                        }
                                        .padding(horizontal = 15.dp, vertical = 10.dp),
                                    color = WineyTheme.colors.gray_50,
                                    style = WineyTheme.typography.captionB1
                                )
                            }
                        }
                    } else {
                        WineCategoryTopbar(
                            onClickTopBarBackIcon = onClickTopBarBackIcon,
                            title = uiState.selectedShopCategory.title
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .align(Alignment.CenterHorizontally)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(42.dp)
                        )
                        .clip(RoundedCornerShape(42.dp))
                        .background(WineyTheme.colors.gray_50)
                        .clickable {
                            viewModel.getWineShops(
                                shopFilter = uiState.selectedShopCategory.toString(),
                                cameraPositionState = appState.cameraPositionState
                            )
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
                        color = WineyTheme.colors.main_2
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_refresh_20),
                        contentDescription = "IC_REFERSH",
                        modifier = Modifier
                            .size(14.dp)
                            .rotate(120f),
                        tint = WineyTheme.colors.main_1
                    )
                }
            }
        }
    }
}

@Composable
private fun WineCategoryTopbar(
    onClickTopBarBackIcon: () -> Unit,
    title: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
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
                        onClickTopBarBackIcon()
                    }
            )
        }
        Text(
            text = title,
            style = WineyTheme.typography.title2.copy(
                fontWeight = FontWeight.Bold,
                color = WineyTheme.colors.gray_50
            )
        )
    }
}


