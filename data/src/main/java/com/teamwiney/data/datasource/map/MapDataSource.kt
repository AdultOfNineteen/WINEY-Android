package com.teamwiney.data.datasource.map

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.BookmarkResult
import com.teamwiney.data.network.model.response.WineShop
import kotlinx.coroutines.flow.Flow

interface MapDataSource {

    fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ): Flow<ApiResult<CommonResponse<List<WineShop>>>>

    fun postBookmark(
        shopId: Int
    ): Flow<ApiResult<CommonResponse<BookmarkResult>>>
}
