package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.AuthenticationMessageCodeResponse
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessageResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {

    fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ): Flow<ApiResult<ResponseWrapper<SocialLoginResponse>>>

    fun sendAuthCodeMessage(
        userId: String,
        request: PhoneNumberRequest
    ): Flow<ApiResult<ResponseWrapper<AuthenticationMessageCodeResponse>>>

    fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ): Flow<ApiResult<VerifyAuthenticationMessageResponse>>
}