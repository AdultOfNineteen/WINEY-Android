package com.teamwiney.data.repository.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.datasource.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteFilters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TastingNoteRepositoryImpl @Inject constructor(
    private val tastingNoteDataSource: TastingNoteDataSource
) : TastingNoteRepository {

    override fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>> =
        tastingNoteDataSource.getTasteAnalysis()

    override fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>> =
        tastingNoteDataSource.getTastingNotes(page, size, order, countries, wineTypes, buyAgain)

    override fun getTastingNotesCount(
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>> =
        tastingNoteDataSource.getTastingNotes(1, 1, order, countries, wineTypes, buyAgain)

    override fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFilters>>> =
        tastingNoteDataSource.getTastingNoteFilters()

    override fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<ResponseWrapper<TastingNoteDetail>>> =
        tastingNoteDataSource.getTastingNoteDetail(noteId)

    override fun deleteTastingNote(noteId: Int): Flow<ApiResult<BaseResponse>> =
        tastingNoteDataSource.deleteTastingNote(noteId)
}