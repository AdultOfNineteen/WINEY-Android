package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.RecommendWineResponse
import com.teamwiney.data.network.model.response.WineTipResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WineService {

    /** 추천 와인 API */
    @GET("/wines/recommend")
    suspend fun getRecommendWines(): ApiResult<ResponseWrapper<List<RecommendWineResponse>>>

    /** 와인 상세보기 조회 API
     * 현재 미구현 */
    @GET("/wines/{wineId}")
    suspend fun getWineDetail(): ApiResult<ResponseWrapper<String>>

    /** 와인 팁 조회 API */
    @GET("/wine-tip")
    suspend fun getWineTips(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResult<ResponseWrapper<PagingData<List<WineTipResponse>>>>

}