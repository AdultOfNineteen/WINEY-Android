package com.teamwiney.data.repository.wine

import com.teamwiney.data.datasource.wine.WineDataSource
import javax.inject.Inject

class WineRepositoryImpl @Inject constructor(
    private val wineDataSource: WineDataSource
) : WineRepository {

    override fun getRecommendWines() = wineDataSource.getRecommendWines()

    override fun getWineDetail(wineId: Long) = wineDataSource.getWineDetail(wineId)

    override fun getWineTips(page: Int, size: Int) = wineDataSource.getWineTips(page, size)
    override fun getSearchWines(
        page: Int,
        size: Int,
        content: String
    ) = wineDataSource.searchWines(page, size, content)

    override fun getSearchWinesCount(
        content: String
    ) = wineDataSource.searchWines(1, 1, content)

}