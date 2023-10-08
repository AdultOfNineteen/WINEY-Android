package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.RecommendWineResponse
import com.teamwiney.data.network.model.response.WineTipResponse
import kotlinx.coroutines.flow.Flow

interface WineDataSource {

    fun getRecommendWines(): Flow<ApiResult<ResponseWrapper<List<RecommendWineResponse>>>>

    fun getWineTips(page: Int, size: Int): Flow<ApiResult<ResponseWrapper<PagingData<List<WineTipResponse>>>>>

}