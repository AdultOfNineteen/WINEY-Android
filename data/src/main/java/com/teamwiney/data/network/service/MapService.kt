package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.BookmarkResult
import com.teamwiney.data.network.model.response.WineShop
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {


    /** 05-01 와인 판매지 지도 */
    @POST("/shops")
    suspend fun getWineShops(
        @Query("shopFilter") shopFilter: String,
        @Body mapPosition: MapPosition
    ): ApiResult<CommonResponse<List<WineShop>>>

    /** 05-02 와인 상점 북마크 취소, 북마크 기능 */
    @POST("/shops/bookmark/{shopId}")
    suspend fun postBookmark(
        @Path("shopId") shopId: Int
    ): ApiResult<CommonResponse<BookmarkResult>>

}