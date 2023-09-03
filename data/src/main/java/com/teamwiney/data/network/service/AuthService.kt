package com.teamwiney.data.network.service

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.AuthenticationMessageCodeResponse
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessageResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

enum class SocialType {
    @SerializedName("KAKAO")
    KAKAO,

    @SerializedName("GOOGLE")
    GOOGLE,

    @SerializedName("normal")
    normal
}

interface AuthService {

    /** 소셜로그인 API */
    @POST("/login/{social}")
    suspend fun socialLogin(
        @Path("social") social: SocialType,
        @Body socialLoginRequest: SocialLoginRequest
    ): ApiResult<ResponseWrapper<SocialLoginResponse>>

    /** 인증번호 전송 API */
    @POST("/users/{userId}/phone/code/send")
    suspend fun sendAuthCodeMessage(
        @Path("userId") userId: String,
        @Body phoneNumberRequest: PhoneNumberRequest
    ): ApiResult<ResponseWrapper<AuthenticationMessageCodeResponse>>

    /** 인증번호 검사 API */
    @POST("/users/{userId}/phone/code/verify")
    suspend fun verifyAuthCodeMessage(
        @Path("userId") userId: String,
        @Body phoneNumberWithVerificationCodeRequest: PhoneNumberWithVerificationCodeRequest
    ): ApiResult<VerifyAuthenticationMessageResponse>

}