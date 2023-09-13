package com.teamwiney.data.repository.auth

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.response.AuthenticationMessageCodeResponse
import com.teamwiney.data.network.model.response.GoogleAccessTokenResponse
import com.teamwiney.data.network.model.response.SetPreferencesResponse
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessageResponse
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getGoogleAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        idToken: String
    ): Flow<ApiResult<GoogleAccessTokenResponse>>

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<ResponseWrapper<SocialLoginResponse>>>

    fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ): Flow<ApiResult<ResponseWrapper<VerifyAuthenticationMessageResponse>>>

    fun sendAuthCodeMessage(
        userId: String,
        phoneNumberRequest: PhoneNumberRequest
    ): Flow<ApiResult<ResponseWrapper<AuthenticationMessageCodeResponse>>>

    fun setPreferences(
        userId: String,
        request: SetPreferencesRequest
    ): Flow<ApiResult<ResponseWrapper<SetPreferencesResponse>>>
}