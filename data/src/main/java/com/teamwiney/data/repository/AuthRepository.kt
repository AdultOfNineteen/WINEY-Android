package com.teamwiney.data.repository

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.response.AuthenticationMessageCodeResponse
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessageResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<ResponseWrapper<SocialLoginResponse>>>

    fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ): Flow<ApiResult<VerifyAuthenticationMessageResponse>>

    fun sendAuthCodeMessage(
        userId: String,
        phoneNumberRequest: PhoneNumberRequest
    ): Flow<ApiResult<ResponseWrapper<AuthenticationMessageCodeResponse>>>
}