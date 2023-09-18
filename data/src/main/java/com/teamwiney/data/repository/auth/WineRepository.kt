package com.teamwiney.data.repository.auth

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.RecommendWineResponse
import kotlinx.coroutines.flow.Flow

interface WineRepository {

    fun getRecommendWines(): Flow<ApiResult<ResponseWrapper<RecommendWineResponse>>>

}