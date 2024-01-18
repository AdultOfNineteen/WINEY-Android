package com.teamwiney.data.repository.map

import com.teamwiney.data.datasource.map.MapDataSource
import com.teamwiney.data.network.model.request.MapPosition
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapDataSource
) : MapRepository {

    override fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ) = mapDataSource.getWineShops(shopFilter, mapPosition)
}
