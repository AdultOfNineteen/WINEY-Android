package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineTip
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WineService {

    /** 추천 와인 API */
    @GET("/wines/recommend")
    suspend fun getRecommendWines(): ApiResult<ResponseWrapper<List<RecommendWine>>>

    /** 와인 상세정보 조회 API */
    @GET("/wines/{wineId}")
    suspend fun getWineDetail(
        @Path("wineId") wineId: Long
    ): ApiResult<ResponseWrapper<Wine>>

    /** 와인 팁 조회 API */
    @GET("/wine-tip")
    suspend fun getWineTips(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResult<ResponseWrapper<PagingResponse<List<WineTip>>>>

}