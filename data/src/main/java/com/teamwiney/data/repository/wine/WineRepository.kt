package com.teamwiney.data.repository.wine

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.RecommendWine
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.model.response.Wine
import com.teamwiney.data.network.model.response.WineTip
import kotlinx.coroutines.flow.Flow

interface WineRepository {

    fun getRecommendWines(): Flow<ApiResult<CommonResponse<List<RecommendWine>>>>

    fun getWineDetail(wineId: Long): Flow<ApiResult<CommonResponse<Wine>>>

    fun getWineTips(page: Int, size: Int): Flow<ApiResult<CommonResponse<PagingResponse<List<WineTip>>>>>

    fun getSearchWines(page: Int, size: Int, content: String): Flow<ApiResult<CommonResponse<PagingResponse<List<SearchWine>>>>>

    fun getSearchWinesCount(content: String): Flow<ApiResult<CommonResponse<PagingResponse<List<SearchWine>>>>>
}