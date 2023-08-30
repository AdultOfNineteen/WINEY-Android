package com.teamwiney.data.datasource

import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthDataSource {

    override fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ) = flow {
        emit(authService.socialLogin(socialType, socialLoginRequest))
    }.flowOn(ioDispatcher)

    override fun sendAuthCodeMessage(
        phoneNumberRequest: PhoneNumberRequest
    ) = flow {
        emit(authService.sendAuthCodeMessage(userId = 0, phoneNumberRequest = phoneNumberRequest))
    }.flowOn(ioDispatcher)
}