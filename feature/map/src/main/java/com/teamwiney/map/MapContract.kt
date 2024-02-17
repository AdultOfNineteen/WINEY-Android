package com.teamwiney.map

import androidx.navigation.NavOptions
import com.naver.maps.geometry.LatLng
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.map.MapViewModel.Companion.DEFAULT_LATLNG
import com.teamwiney.map.model.MovingCameraWrapper
import com.teamwiney.map.model.ShopCategory

class MapContract {

    data class State(
        val isLoading: Boolean = false,
        val wineShops: List<WineShop> = emptyList(),
        val userPosition: LatLng = DEFAULT_LATLNG,
        val movingCameraPosition: MovingCameraWrapper = MovingCameraWrapper.DEFAULT,
        val selectedMarkar: WineShop? = null,
        val selectedShopCategory: ShopCategory = ShopCategory.ALL,
        val previousCateogy: ShopCategory? = null
    ) : UiState

    sealed class Event : UiEvent {
    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()
    }

}