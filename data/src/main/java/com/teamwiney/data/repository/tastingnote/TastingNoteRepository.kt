package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import kotlinx.coroutines.flow.Flow

interface TastingNoteRepository {

    fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysisResponse>>>

    fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>>

}