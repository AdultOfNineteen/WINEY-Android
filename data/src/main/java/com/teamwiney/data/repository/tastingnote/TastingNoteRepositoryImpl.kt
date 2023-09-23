package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.datasource.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysis
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TastingNoteRepositoryImpl @Inject constructor(
    private val tastingNoteDataSource: TastingNoteDataSource
) : TastingNoteRepository {

    override fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>> =
        tastingNoteDataSource.getTasteAnalysis()
}