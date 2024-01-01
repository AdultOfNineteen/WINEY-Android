package com.teamwiney.data.repository.winebadge

import com.teamwiney.data.datasource.winebadge.WineBadgeDataSource
import javax.inject.Inject

class WineBadgeRepositoryImpl @Inject constructor(
    private val wineBadgeDataSource: WineBadgeDataSource
) : WineBadgeRepository {

    override fun getWineBadgeList(userId: Long) = wineBadgeDataSource.getWineBadgeList(userId)

    override fun getWineBadgeDetail(
        userId: Long,
        wineBadgeId: Long
    ) = wineBadgeDataSource.getWineBadgeDetail(userId, wineBadgeId)

}