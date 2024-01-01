package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineBadge
import com.teamwiney.data.network.model.response.WineBadge
import retrofit2.http.GET
import retrofit2.http.Path

interface WineBadgeService {

    /** 와인 뱃지 조회 API */
    @GET("/users/{userId}/wine-badges")
    suspend fun getUserWineBadgeList(
        @Path("userId") userId: Long
    ): ApiResult<ResponseWrapper<UserWineBadge>>

    /** 와인 상세 조회 API */
    @GET("/users/{userId}/wine-badges/{wineBadgeId}")
    suspend fun getWineBadgeDetail(
        @Path("userId") userId: Long,
        @Path("wineBadgeId") windBadgeId: Long
    ): ApiResult<ResponseWrapper<WineBadge>>

}