package com.teamwiney.data.repository

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.domain.response.AccessTokenResponse
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.response.AuthentificationMessageCodeResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<ResponseWrapper<AccessTokenResponse>>>

    fun sendAuthCodeMessage(phoneNumberRequest: PhoneNumberRequest): Flow<ApiResult<ResponseWrapper<AuthentificationMessageCodeResponse>>>
}