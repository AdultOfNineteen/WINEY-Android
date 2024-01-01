package com.teamwiney.data.datasource.winebadge

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineBadge
import com.teamwiney.data.network.model.response.WineBadge
import kotlinx.coroutines.flow.Flow

interface WineBadgeDataSource {

    fun getWineBadgeList(
        userId: Long
    ): Flow<ApiResult<ResponseWrapper<UserWineBadge>>>

    fun getWineBadgeDetail(
        userId: Long,
        wineBadgeId: Long
    ): Flow<ApiResult<ResponseWrapper<WineBadge>>>

}