package com.teamwiney.data.datasource.winebadge

import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.service.WineBadgeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WineBadgeDataSourceImpl @Inject constructor(
    private val wineBadgeService: WineBadgeService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WineBadgeDataSource {

    override fun getWineBadgeList(userId: Long) = flow {
        emit(wineBadgeService.getUserWineBadgeList(userId))
    }.flowOn(ioDispatcher)

    override fun getWineBadgeDetail(
        userId: Long,
        wineBadgeId: Long
    ) = flow {
        emit(wineBadgeService.getWineBadgeDetail(userId, wineBadgeId))
    }.flowOn(ioDispatcher)

}