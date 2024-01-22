package com.teamwiney.data.datasource.auth

import com.teamwiney.core.common.model.SocialType
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.model.request.FcmTokenRequest
import com.teamwiney.data.network.model.request.GoogleAccessTokenRequest
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.service.AuthService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthDataSource {

    override fun getGoogleAccessToken(
        googleAccessTokenRequest: GoogleAccessTokenRequest
    ) = flow {
        emit(authService.getGoogleAccessToken(googleAccessTokenRequest = googleAccessTokenRequest))
    }.flowOn(ioDispatcher)

    override fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ) = flow {
        emit(authService.socialLogin(socialType, socialLoginRequest))
    }.flowOn(ioDispatcher)

    override fun sendAuthCodeMessage(
        userId: String,
        request: PhoneNumberRequest
    ) = flow {
        emit(authService.sendAuthCodeMessage(userId = userId, phoneNumberRequest = request))
    }.flowOn(ioDispatcher)

    override fun verifyAuthCodeMessage(
        userId: String,
        request: PhoneNumberWithVerificationCodeRequest
    ) = flow {
        emit(
            authService.verifyAuthCodeMessage(
                userId = userId,
                phoneNumberWithVerificationCodeRequest = request
            )
        )
    }.flowOn(ioDispatcher)

    override fun setPreferences(
        userId: String,
        preferences: SetPreferencesRequest
    ) = flow {
        emit(authService.setPreferences(userId, preferences))
    }.flowOn(ioDispatcher)

    override fun refreshToken(refreshToken: String) = flow {
        emit(authService.refreshToken(refreshToken))
    }.flowOn(ioDispatcher)

    override fun getUserInfo() = flow {
        emit(authService.getUserInfo())
    }.flowOn(ioDispatcher)

    override fun getConnections() = flow {
        emit(authService.getConnections())
    }.flowOn(ioDispatcher)

    override fun registerFcmToken(fcmTokenRequest: FcmTokenRequest) = flow {
        emit(authService.registerFcmToken(fcmTokenRequest))
    }.flowOn(ioDispatcher)

    override fun deleteUser(
        userId: String,
        reason: String
    ) = flow {
        emit(authService.deleteUser(userId, reason))
    }.flowOn(ioDispatcher)

    override fun logOut(deviceId: String) = flow {
        emit(authService.logOut(deviceId))
    }.flowOn(ioDispatcher)

}