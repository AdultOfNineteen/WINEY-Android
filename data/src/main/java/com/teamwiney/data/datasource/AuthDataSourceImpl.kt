package com.teamwiney.data.datasource

import com.teamwiney.core.common.base.ResponseWrapper
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.GoogleAccessTokenRequest
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.network.model.request.SetPreferencesRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.SetPreferencesResponse
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Body
import retrofit2.http.Path
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

}