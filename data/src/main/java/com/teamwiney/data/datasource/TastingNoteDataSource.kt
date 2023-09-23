package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysis
import kotlinx.coroutines.flow.Flow

interface TastingNoteDataSource {

    fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>>
}