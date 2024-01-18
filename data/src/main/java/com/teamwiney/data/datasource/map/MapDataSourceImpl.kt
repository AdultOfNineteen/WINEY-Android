package com.teamwiney.data.datasource.map

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.data.network.service.MapService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(
    private val mapService: MapService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MapDataSource {

    override fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ): Flow<ApiResult<ResponseWrapper<List<WineShop>>>> = flow {
        emit(mapService.getWineShops(shopFilter, mapPosition))
    }.flowOn(ioDispatcher)
}