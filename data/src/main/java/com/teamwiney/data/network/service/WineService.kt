package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineTip
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WineService {

    /** 추천 와인 API */
    @GET("/wines/recommend")
    suspend fun getRecommendWines(): ApiResult<CommonResponse<List<RecommendWine>>>

    /** 와인 상세정보 조회 API */
    @GET("/wines/{wineId}")
    suspend fun getWineDetail(
        @Path("wineId") wineId: Long
    ): ApiResult<CommonResponse<Wine>>

    /** 와인 팁 조회 API */
    @GET("/wine-tip")
    suspend fun getWineTips(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResult<CommonResponse<PagingResponse<List<WineTip>>>>


    /** 와인 검색 API */
    @GET("/wines/search")
    suspend fun searchWines(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("content") content: String
    ): ApiResult<CommonResponse<PagingResponse<List<SearchWine>>>>
}