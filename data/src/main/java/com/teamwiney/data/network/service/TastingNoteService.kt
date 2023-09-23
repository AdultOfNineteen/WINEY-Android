package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysis
import retrofit2.http.GET

interface TastingNoteService {

    /** 내 취향 분석 */
    @GET("/tasting-notes/taste-analysis")
    suspend fun getTasteAnalysis(): ApiResult<ResponseWrapper<TasteAnalysis>>
}