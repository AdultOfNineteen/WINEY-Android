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
import com.teamwiney.data.network.model.response.TastingNoteIdRes
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override fun postTastingNote(
        wineNoteWriteRequest: HashMap<String, RequestBody>,
        smellKeywordList: List<MultipartBody.Part>,
        multipartFiles: List<MultipartBody.Part>
    ): Flow<ApiResult<ResponseWrapper<TastingNoteIdRes>>> {
        return tastingNoteDataSource.postTastingNote(
            wineNoteWriteRequest,
            smellKeywordList,
            multipartFiles
        )
    }
}