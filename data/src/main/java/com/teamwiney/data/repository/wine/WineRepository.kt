package com.teamwiney.data.repository.wine

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineTip
import kotlinx.coroutines.flow.Flow

interface WineRepository {

    fun getRecommendWines(): Flow<ApiResult<ResponseWrapper<List<RecommendWine>>>>

    fun getWineDetail(wineId: Long): Flow<ApiResult<ResponseWrapper<Wine>>>

    fun getWineTips(page: Int, size: Int): Flow<ApiResult<ResponseWrapper<PagingResponse<List<WineTip>>>>>

    fun searchWines(page: Int, size: Int, content: String): Flow<ApiResult<ResponseWrapper<PagingResponse<List<SearchWine>>>>>
}