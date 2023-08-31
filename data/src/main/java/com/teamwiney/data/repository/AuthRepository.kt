package com.teamwiney.data.repository

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.domain.response.SocailLoginResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.response.AuthentificationMessageCodeResponse
import com.teamwiney.data.network.model.response.VerifyAuthentificationMessageResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<ResponseWrapper<SocailLoginResponse>>>

    fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ): Flow<ApiResult<VerifyAuthentificationMessageResponse>>

    fun sendAuthCodeMessage(
        userId: String,
        phoneNumberRequest: PhoneNumberRequest
    ): Flow<ApiResult<ResponseWrapper<AuthentificationMessageCodeResponse>>>
}