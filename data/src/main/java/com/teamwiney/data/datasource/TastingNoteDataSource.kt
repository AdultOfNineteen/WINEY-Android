package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import com.teamwiney.data.network.model.response.TastingNoteResponse
import kotlinx.coroutines.flow.Flow

interface TastingNoteDataSource {

    fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysisResponse>>>

    fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingData<List<TastingNoteResponse>>>>>

    fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>>

}