package com.teamwiney.data.datasource.tastingnote

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteDetail
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

    override fun getTasteAnalysis(): Flow<ApiResult<ResponseWrapper<TasteAnalysis>>> =
        flow {
            emit(tastingNoteService.getTasteAnalysis())
        }.flowOn(ioDispatcher)

    override fun getTastingNotes(
        page: Int,
        size: Int,
        order: Int,
        countries: List<String>,
        wineTypes: List<String>,
        buyAgain: Int?
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>> =
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

    override fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFilters>>> =
        flow {
            emit(tastingNoteService.getTastingNoteFilters())
        }.flowOn(ioDispatcher)

    override fun getTastingNoteDetail(noteId: Int): Flow<ApiResult<ResponseWrapper<TastingNoteDetail>>> =
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
    ): Flow<ApiResult<ResponseWrapper<TastingNoteIdRes>>> = flow {
        emit(tastingNoteService.postTastingNote(request, multipartFiles))
    }.flowOn(ioDispatcher)

}