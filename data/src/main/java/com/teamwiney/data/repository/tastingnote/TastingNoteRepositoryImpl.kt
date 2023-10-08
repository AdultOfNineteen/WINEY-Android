package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.datasource.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingData
import com.teamwiney.data.network.model.response.TasteAnalysisResponse
import com.teamwiney.data.network.model.response.TastingNoteFiltersResponse
import com.teamwiney.data.network.model.response.TastingNoteResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TastingNoteRepositoryImpl @Inject constructor(
    private val tastingNoteDataSource: TastingNoteDataSource
) : TastingNoteRepository {

    override fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysisResponse>>> =
        tastingNoteDataSource.getTasteAnalysis()

    override fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingData<List<TastingNoteResponse>>>>> =
        tastingNoteDataSource.getTastingNotes(page, size, order, countries, wineTypes, buyAgain)

    override fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFiltersResponse>>> =
        tastingNoteDataSource.getTastingNoteFilters()

}