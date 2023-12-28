package com.teamwiney.data.datasource.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
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

interface TastingNoteDataSource {

    fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>>

    fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>>

    fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFilters>>>

    fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<ResponseWrapper<TastingNoteDetail>>>
    fun deleteTastingNote(noteId: Int): Flow<ApiResult<ResponseWrapper<String>>>

    fun postTastingNote(
        request: RequestBody,
        multipartFiles: List<MultipartBody.Part>,
    ): Flow<ApiResult<ResponseWrapper<TastingNoteIdRes>>>
}