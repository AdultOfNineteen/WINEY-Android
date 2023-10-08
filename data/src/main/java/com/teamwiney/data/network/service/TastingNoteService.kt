package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import retrofit2.http.GET

interface TastingNoteService {

    /** 내 취향 분석 */
    @GET("/tasting-notes/taste-analysis")
    suspend fun getTasteAnalysis(): ApiResult<ResponseWrapper<TasteAnalysisResponse>>

    /** 테이스팅 노트 필터 목록 조회 API */
    @GET("/tasting-notes/filter")
    suspend fun getTastingNoteFilters(): ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>
}