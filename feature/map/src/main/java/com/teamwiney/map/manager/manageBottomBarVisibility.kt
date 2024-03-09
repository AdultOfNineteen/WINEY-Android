package com.teamwiney.map.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.teamwiney.map.MapContract
import com.teamwiney.map.model.ShopCategory

@Composable
fun manageBottomBarVisibility(
    uiState: MapContract.State,
    updateIsMapDetail: (Boolean) -> Unit
) {
    DisposableEffect(uiState.selectedMarkar, uiState.selectedShopCategory) {
        if (uiState.selectedMarkar == null && uiState.selectedShopCategory == ShopCategory.ALL) {
            updateIsMapDetail(false)
        } else {
            updateIsMapDetail(true)
        }
        onDispose {
            updateIsMapDetail(false)
        }
    }
}
