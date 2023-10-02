package com.teamwiney.data.datasource

import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.service.WineService
import kotlinx.coroutines.CoroutineDispatcher
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

}