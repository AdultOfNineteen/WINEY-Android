package com.teamwiney.data.repository.winegrade

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard
import kotlinx.coroutines.flow.Flow

interface WineGradeRepository {

    fun getUserWineGrade(
        userId: String
    ): Flow<ApiResult<CommonResponse<UserWineGrade>>>

    fun getWineGradeStandard(): Flow<ApiResult<CommonResponse<List<WineGradeStandard>>>>

}