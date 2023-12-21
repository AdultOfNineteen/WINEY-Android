package com.teamwiney.data.repository.winegrade

import com.teamwiney.data.datasource.winegrade.WineGradeDataSource
import javax.inject.Inject

class WineGradeRepositoryImpl @Inject constructor(
    private val wineGradeDataSource: WineGradeDataSource
) : WineGradeRepository {

    override fun getUserWineGrade(
        userId: String
    ) = wineGradeDataSource.getUserWineGrade(userId)

    override fun getWineGradeStandard() = wineGradeDataSource.getWineGradeStandard()

}