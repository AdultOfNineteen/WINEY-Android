package com.teamwiney.data.repository

import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<BaseResponse>>

    fun kakaoLogin(accessToken: String): Flow<ApiResult<BaseResponse>>
}