package com.teamwiney.data.repository

import com.teamwiney.data.datasource.AuthDataSource
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.service.SocialType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun socialLogin(
        socialType: SocialType,
        accessToken: String
    ) = authDataSource.socialLogin(socialType, SocialLoginRequest(accessToken))

    override fun sendAuthCodeMessage(
        phoneNumberRequest: PhoneNumberRequest
    ) = authDataSource.sendAuthCodeMessage(phoneNumberRequest)
}