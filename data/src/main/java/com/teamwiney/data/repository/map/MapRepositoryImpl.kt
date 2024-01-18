package com.teamwiney.data.repository.map

import android.content.Context
import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.datasource.map.MapDataSource
import com.teamwiney.data.datasource.map.MapDataSourceImpl
import com.teamwiney.data.datasource.tastingnote.TastingNoteDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.MapPosition
import com.teamwiney.data.network.model.response.TasteAnalysis
import com.teamwiney.data.network.model.response.WineShop
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapDataSource
) : MapRepository {

    override fun getWineShops(
        shopFilter: String,
        mapPosition: MapPosition
    ) = mapDataSource.getWineShops(shopFilter, mapPosition)
}
