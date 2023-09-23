package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.TasteAnalysis
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

}