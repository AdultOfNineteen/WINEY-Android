package com.teamwiney.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.map.model.ShopCategory
import com.teamwiney.ui.theme.WineyTheme

@Composable
fun MapBottomSheetContent(
    contentHeight: Dp,
    shopCategory: ShopCategory,
    wineShops: List<WineShop>,
    selectedMarker: WineShop?,
    postBookmark: (WineShop) -> Unit,
    userPosition: LatLng,
    setSelectedMarker: (WineShop) -> Unit
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
            if (selectedMarker != null) {
                WineShopDetail(
                    wineShop = selectedMarker,
                    postBookmark = postBookmark,
                    userPosition = userPosition
                )
            } else {
                when (shopCategory) {
                    // 내 장소
                    ShopCategory.LIKE -> {
                        WineShopBookmark(
                            wineShops = wineShops,
                            postBookmark = postBookmark,
                            setSelectedMarker = setSelectedMarker
                        )
                    }

                    // 리스트
                    else -> {
                        WineShopList(
                            wineShops = wineShops,
                            postBookmark = postBookmark,
                            setSelectedMarker = setSelectedMarker
                        )
                    }
                }
            }
        }
    }
}


