package com.teamwiney.data.datasource.winegrade

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.UserWineGrade
import com.teamwiney.data.network.model.response.WineGradeStandard
import kotlinx.coroutines.flow.Flow

interface WineGradeDataSource {

    fun getUserWineGrade(
        userId: String
    ): Flow<ApiResult<ResponseWrapper<UserWineGrade>>>

    fun getWineGradeStandard(): Flow<ApiResult<ResponseWrapper<WineGradeStandard>>>

}