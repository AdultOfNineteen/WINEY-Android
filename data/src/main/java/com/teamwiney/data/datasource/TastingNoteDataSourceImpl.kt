package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.TastingNote
import com.teamwiney.data.network.model.response.TastingNoteFilters
import com.teamwiney.data.network.service.TastingNoteService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
        buyAgain: Int
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<TastingNote>>>>> =
        flow {
            emit(tastingNoteService.getTastingNotes(page, size, order, countries, wineTypes, buyAgain))
        }.flowOn(ioDispatcher)

    override fun getTastingNoteFilters(): Flow<ApiResult<ResponseWrapper<TastingNoteFilters>>> =
        flow {
            emit(tastingNoteService.getTastingNoteFilters())
        }.flowOn(ioDispatcher)

}