package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.WineShop
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MapService {


    /** 05-01 와인 판매지 지도 */
    @POST("/shops")
    suspend fun getWineShops(
        @Query("shopFilter") shopFilter: String,
        @Body mapPosition: MapPosition
    ): ApiResult<ResponseWrapper<List<WineShop>>>
}