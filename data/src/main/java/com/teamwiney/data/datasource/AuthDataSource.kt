package com.teamwiney.data.datasource

import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ): Flow<ApiResult<BaseResponse>>

    fun kakaoLogin(accessToken: String): Flow<ApiResult<BaseResponse>>
}