package com.teamwiney.data.network.service

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.model.SocialType
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.FcmTokenRequest
import com.teamwiney.data.network.model.request.GoogleAccessTokenRequest
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.AccessToken
import com.teamwiney.data.network.model.response.AuthenticationMessageCode
import com.teamwiney.data.network.model.response.DeleteUser
import com.teamwiney.data.network.model.response.GoogleAccessToken
import com.teamwiney.data.network.model.response.SetPreferences
import com.teamwiney.data.network.model.response.SocialLogin
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface AuthService {

    /** 구글 액세스 토큰 발급 API */
    @POST
    suspend fun getGoogleAccessToken(
        @Url url: String = "https://www.googleapis.com/oauth2/v4/token",
        @Body googleAccessTokenRequest: GoogleAccessTokenRequest
    ): ApiResult<GoogleAccessToken>

    /** 소셜로그인 API */
    @POST("/login/{social}")
    suspend fun socialLogin(
        @Path("social") social: SocialType,
        @Body socialLoginRequest: SocialLoginRequest
    ): ApiResult<ResponseWrapper<SocialLogin>>

    /** 액세스토큰 재발급 API **/
    @POST("/refresh")
    suspend fun updateToken(): Response<ResponseWrapper<AccessToken>>

    /** 인증번호 전송 API */
    @POST("/users/{userId}/phone/code/send")
    suspend fun sendAuthCodeMessage(
        @Path("userId") userId: String,
        @Body phoneNumberRequest: PhoneNumberRequest
    ): ApiResult<ResponseWrapper<AuthenticationMessageCode>>

    /** 인증번호 검사 API */
    @POST("/users/{userId}/phone/code/verify")
    suspend fun verifyAuthCodeMessage(
        @Path("userId") userId: String,
        @Body phoneNumberWithVerificationCodeRequest: PhoneNumberWithVerificationCodeRequest
    ): ApiResult<ResponseWrapper<VerifyAuthenticationMessage>>

    /** 취향 설정 API */
    @PATCH("/users/{userId}/preferences")
    suspend fun setPreferences(
        @Path("userId") userId: String,
        @Body preferences: SetPreferencesRequest
    ): ApiResult<ResponseWrapper<SetPreferences>>


    /** 토큰 리프레쉬 API */
    @POST("/refresh")
    suspend fun refreshToken(
        @Header("X-REFRESH-TOKEN") refreshToken: String
    ): ApiResult<ResponseWrapper<AccessToken>>

    /** 접속 API */
    @GET("/connections")
    suspend fun getConnections(): ApiResult<BaseResponse>

    /** FCM 토큰 등록 API */
    @POST("/fcm")
    suspend fun registerFcmToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): ApiResult<BaseResponse>

    /** 회원 탈퇴 API */
    @DELETE("/users/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: String,
        @Query("reason") reason: String
    ): ApiResult<ResponseWrapper<DeleteUser>>

    /** 로그아웃 API */
    @GET("/users/logout")
    suspend fun logOut(
        @Query("deviceId") deviceId: String
    ): ApiResult<BaseResponse>
}