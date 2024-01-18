package com.teamwiney.data.datasource.map

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.UserWineBadge
import com.teamwiney.data.network.model.response.WineShop
import kotlinx.coroutines.flow.Flow

interface MapDataSource {

    fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ): Flow<ApiResult<ResponseWrapper<List<WineShop>>>>
}
