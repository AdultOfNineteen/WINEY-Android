package com.teamwiney.data.repository.winebadge

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineBadge
import com.teamwiney.data.network.model.response.WineBadge
import kotlinx.coroutines.flow.Flow

interface WineBadgeRepository {

    fun getWineBadgeList(
        userId: Long
    ): Flow<ApiResult<ResponseWrapper<UserWineBadge>>>

    fun getWineBadgeDetail(
        userId: Long,
        wineBadgeId: Long
    ): Flow<ApiResult<ResponseWrapper<WineBadge>>>

}