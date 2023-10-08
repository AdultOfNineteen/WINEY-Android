package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import com.teamwiney.data.network.model.response.TastingNoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TastingNoteService {

    /** 내 취향 분석 API */
    @GET("/tasting-notes/taste-analysis")
    suspend fun getTasteAnalysis(): ApiResult<ResponseWrapper<TasteAnalysisResponse>>

    /** 테이스팅 노트 조회 API */
    @GET("/tasting-notes")
    suspend fun getTastingNotes(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("order") order: Int,
        @Query("countries") countries: List<String>,
        @Query("wineTypes") wineTypes: List<String>,
        @Query("buyAgain") buyAgain: Int
    ): ApiResult<ResponseWrapper<PagingData<List<TastingNoteResponse>>>>

    /** 테이스팅 노트 필터 목록 조회 API */
    @GET("/tasting-notes/filter")
    suspend fun getTastingNoteFilters(): ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>
}