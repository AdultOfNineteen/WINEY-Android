package com.teamwiney.data.repository.auth

import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.core.common.model.SocialType
import com.teamwiney.core.common.`typealias`.BaseResponse
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.response.AccessToken
import com.teamwiney.data.network.model.response.AuthenticationMessageCode
import com.teamwiney.data.network.model.response.DeleteUser
import com.teamwiney.data.network.model.response.GoogleAccessToken
import com.teamwiney.data.network.model.response.SetPreferences
import com.teamwiney.data.network.model.response.SocialLogin
import com.teamwiney.data.network.model.response.UserInfo
import com.teamwiney.data.network.model.response.VerifyAuthenticationMessage
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getGoogleAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        idToken: String
    ): Flow<ApiResult<GoogleAccessToken>>

    fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ): Flow<ApiResult<CommonResponse<SocialLogin>>>

    fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ): Flow<ApiResult<CommonResponse<VerifyAuthenticationMessage>>>

    fun sendAuthCodeMessage(
        userId: String,
        phoneNumberRequest: PhoneNumberRequest
    ): Flow<ApiResult<CommonResponse<AuthenticationMessageCode>>>

    fun setPreferences(
        userId: String,
        request: SetPreferencesRequest
    ): Flow<ApiResult<CommonResponse<SetPreferences>>>

    fun refreshToken(
        refreshToken: String
    ): Flow<ApiResult<CommonResponse<AccessToken>>>

    fun getUserInfo(): Flow<ApiResult<CommonResponse<UserInfo>>>

    fun getConnections(): Flow<ApiResult<BaseResponse>>

    fun registerFcmToken(
        fcmToken: String,
        deviceId: String
    ): Flow<ApiResult<BaseResponse>>

    fun deleteUser(
        userId: String,
        reason: String
    ): Flow<ApiResult<CommonResponse<DeleteUser>>>

    fun logOut(deviceId: String): Flow<ApiResult<BaseResponse>>

}