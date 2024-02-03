package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard
import retrofit2.http.GET
import retrofit2.http.Path

interface WineGradeService {

    /** 와인 등급 조회 API */
    @GET("/users/{userId}/wine-grade")
    suspend fun getUserWineGrade(
        @Path("userId") userId: String
    ): ApiResult<CommonResponse<UserWineGrade>>

    /** 와인 등급 기준 조회 API */
    @GET("/users/wine-grades")
    suspend fun getWineGradeStandard(): ApiResult<CommonResponse<List<WineGradeStandard>>>

}