package com.teamwiney.data.datasource

import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ): Flow<ApiResult<SocialLoginResponse>>

}