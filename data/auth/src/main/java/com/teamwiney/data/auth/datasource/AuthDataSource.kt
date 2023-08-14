package com.teamwiney.data.auth.datasource

import com.teamwiney.core.network.adapter.ApiResult
import com.teamwiney.core.network.model.request.SocialLoginRequest
import com.teamwiney.core.network.model.response.SocialLoginResponse
import com.teamwiney.core.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ): Flow<ApiResult<SocialLoginResponse>>

}