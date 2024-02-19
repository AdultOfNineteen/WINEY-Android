package com.teamwiney.data.datasource.tastingnote

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
import com.teamwiney.data.network.model.response.TastingNoteExists
import com.teamwiney.data.network.model.response.TastingNoteFilters
import com.teamwiney.data.network.model.response.TastingNoteIdRes
import com.teamwiney.data.network.service.TastingNoteService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class TastingNoteDataSourceImpl @Inject constructor(
    private val tastingNoteService: TastingNoteService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TastingNoteDataSource {

    override fun getTasteAnalysis(): Flow<ApiResult<CommonResponse<TasteAnalysis>>> =
        flow {
            emit(tastingNoteService.getTasteAnalysis())
        }.flowOn(ioDispatcher)

    override fun getCheckTastingNotes(): Flow<ApiResult<CommonResponse<TastingNoteExists>>> =
        flow {
            emit(tastingNoteService.getCheckTastingNotes())
        }.flowOn(ioDispatcher)

    override fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?
    ): Flow<ApiResult<CommonResponse<PagingResponse<List<TastingNote>>>>> =
        flow {
            emit(
                tastingNoteService.getTastingNotes(
                    page,
                    size,
                    order,
                    countries,
                    wineTypes,
                    buyAgain
                )
            )
        }.flowOn(ioDispatcher)

    override fun getTastingNoteFilters(): Flow<ApiResult<CommonResponse<TastingNoteFilters>>> =
        flow {
            emit(tastingNoteService.getTastingNoteFilters())
        }.flowOn(ioDispatcher)

    override fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<CommonResponse<TastingNoteDetail>>> =
        flow {
            emit(tastingNoteService.getTastingNoteDetail(noteId))
        }.flowOn(ioDispatcher)

    override fun deleteTastingNote(noteId: Int): Flow<ApiResult<BaseResponse>> =
        flow {
            emit(tastingNoteService.deleteTastingNote(noteId))
        }.flowOn(ioDispatcher)

    override fun postTastingNote(
        request: RequestBody,
        multipartFiles: List<MultipartBody.Part>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>> = flow {
        emit(tastingNoteService.postTastingNote(request, multipartFiles))
    }.flowOn(ioDispatcher)

    override fun updateTastingNote(
        noteId: Int,
        request: RequestBody,
        multipartFiles: List<MultipartBody.Part>
    ): Flow<ApiResult<CommonResponse<TastingNoteIdRes>>> = flow {
        emit(tastingNoteService.updateTastingNote(noteId, request, multipartFiles))
    }.flowOn(ioDispatcher)

}