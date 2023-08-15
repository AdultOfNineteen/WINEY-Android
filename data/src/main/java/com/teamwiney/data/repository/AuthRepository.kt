package com.teamwiney.data.repository

import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<SocialLoginResponse>>

}