package com.teamwiney.data.datasource.wine

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.PagingResponse
import com.teamwiney.data.network.model.response.SearchWine
import com.teamwiney.data.network.service.WineService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WineDataSourceImpl @Inject constructor(
    private val wineService: WineService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WineDataSource {

    override fun getRecommendWines() = flow {
        emit(wineService.getRecommendWines())
    }.flowOn(ioDispatcher)

    override fun getWineDetail(wineId: Long) = flow {
        emit(wineService.getWineDetail(wineId))
    }.flowOn(ioDispatcher)

    override fun getWineTips(page: Int, size: Int) = flow {
        emit(wineService.getWineTips(page, size))
    }.flowOn(ioDispatcher)

    override fun searchWines(
        page: Int,
        size: Int,
        content: String
    ): Flow<ApiResult<ResponseWrapper<PagingResponse<List<SearchWine>>>>> = flow {
        emit(wineService.searchWines(page, size, content))
    }.flowOn(ioDispatcher)

}