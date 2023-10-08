package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.datasource.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TastingNoteRepositoryImpl @Inject constructor(
    private val tastingNoteDataSource: TastingNoteDataSource
) : TastingNoteRepository {

    override fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysisResponse>>> =
        tastingNoteDataSource.getTasteAnalysis()

    override fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>> =
        tastingNoteDataSource.getTastingNoteFilters()

}