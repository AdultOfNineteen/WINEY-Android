@file:OptIn(ExperimentalFoundationApi::class)

package com.teamwiney.map.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamwiney.core.design.R
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.ui.components.HeightSpacer
import com.teamwiney.ui.components.HeightSpacerWithLine
import com.teamwiney.ui.theme.WineyTheme


@Composable
fun ColumnScope.WineShopBookmark(
    wineShops: List<WineShop>, postBookmark: (WineShop) -> Unit,
    setSelectedMarker: (WineShop) -> Unit
) {
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

    if (wineShops.none { it.like }) {
        WineShopEmpty()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = wineShops.filter { it.like },
                key = { item: WineShop -> item.shopId }
            ) { wineShop ->
                WineShopItem(wineShop, postBookmark, setSelectedMarker)
            }
        }
    }
}