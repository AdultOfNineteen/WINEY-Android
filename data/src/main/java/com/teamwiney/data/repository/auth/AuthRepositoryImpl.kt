package com.teamwiney.data.repository.auth

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.core.common.model.SocialType
import com.teamwiney.data.datasource.auth.AuthDataSource
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.FcmTokenRequest
import com.teamwiney.data.network.model.request.GoogleAccessTokenRequest
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.AccessToken
import com.teamwiney.data.network.model.response.SetPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun getGoogleAccessToken(
        clientId: String,
        clientSecret: String,
        code: String,
        idToken: String
    ) = authDataSource.getGoogleAccessToken(
        GoogleAccessTokenRequest(
            grantType = "authorization_code",
            clientId = clientId,
            clientSecret = clientSecret,
            redirectUri = "",
            code = code,
            idToken = idToken
        )
    )

    override fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ) = authDataSource.socialLogin(socialType, SocialLoginRequest(accessToken))

    override fun sendAuthCodeMessage(
        userId: String,
        phoneNumberRequest: PhoneNumberRequest
    ) = authDataSource.sendAuthCodeMessage(userId, phoneNumberRequest)

    override fun setPreferences(
        userId: String,
        request: SetPreferencesRequest
    ): Flow<ApiResult<ResponseWrapper<SetPreferences>>> =
        authDataSource.setPreferences(userId, request)

    override fun refreshToken(refreshToken: String): Flow<ApiResult<ResponseWrapper<AccessToken>>> =
        authDataSource.refreshToken(refreshToken)

    override fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ) = authDataSource.verifyAuthCodeMessage(userId, request)

    override fun getUserInfo() = authDataSource.getUserInfo()

    override fun getConnections() = authDataSource.getConnections()

    override fun registerFcmToken(
        fcmToken: String,
        deviceId: String
    ) = authDataSource.registerFcmToken(
        FcmTokenRequest(fcmToken, deviceId)
    )

    override fun deleteUser(
        userId: String,
        reason: String
    ) = authDataSource.deleteUser(userId, reason)

    override fun logOut(deviceId: String) = authDataSource.logOut(deviceId)

}