package com.teamwiney.map.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teamwiney.data.network.model.response.WineShop

@Composable
fun ColumnScope.WineShopList(
    wineShops: List<WineShop>,
    postBookmark: (WineShop) -> Unit,
    setSelectedMarker: (WineShop) -> Unit
) {
    if (wineShops.isEmpty()) {
        WineShopEmpty()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = wineShops,
                key = { item: WineShop -> item.shopId }
            ) { wineShop ->
                WineShopItem(wineShop, postBookmark, setSelectedMarker)
            }
        }
    }
}