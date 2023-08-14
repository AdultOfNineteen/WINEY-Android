package com.teamwiney.data.auth.datasource

import com.teamwiney.core.network.model.request.SocialLoginRequest
import com.teamwiney.core.network.service.AuthService
import com.teamwiney.core.network.service.SocialType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthDataSource {

    override fun socialLogin(
        socialType: SocialType,
        socialLoginRequest: SocialLoginRequest
    ) = flow {
        emit(authService.socialLogin(socialType, socialLoginRequest))
    }.flowOn(ioDispatcher)

}