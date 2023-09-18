package com.teamwiney.data.repository.wine

import com.teamwiney.data.datasource.WineDataSource
import javax.inject.Inject

class WineRepositoryImpl @Inject constructor(
    private val wineDataSource: WineDataSource
) : WineRepository {

    override fun getRecommendWines() = wineDataSource.getRecommendWines()

}