package com.teamwiney.data.datasource.winegrade

import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.service.WineGradeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WineGradeDataSourceImpl @Inject constructor(
    private val wineGradeService: WineGradeService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WineGradeDataSource {

    override fun getUserWineGrade(userId: String) = flow {
        emit(wineGradeService.getUserWineGrade(userId))
    }.flowOn(ioDispatcher)

    override fun getWineGradeStandard() = flow {
        emit(wineGradeService.getWineGradeStandard())
    }.flowOn(ioDispatcher)

}