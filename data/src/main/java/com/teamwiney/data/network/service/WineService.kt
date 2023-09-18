package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.RecommendWineResponse
import retrofit2.http.GET

interface WineService {

    /** 추천 와인 API */
    @GET("/wines/recommend")
    suspend fun getRecommendWines(): ApiResult<ResponseWrapper<RecommendWineResponse>>

}