package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.RecommendWineResponse
import kotlinx.coroutines.flow.Flow

interface WineDataSource {

    fun getRecommendWines(): Flow<ApiResult<ResponseWrapper<List<RecommendWineResponse>>>>

}