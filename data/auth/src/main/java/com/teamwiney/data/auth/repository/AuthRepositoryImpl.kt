package com.teamwiney.data.auth.repository

import com.teamwiney.core.network.service.SocialType
import com.teamwiney.data.auth.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override fun socialLogin(socialType: SocialType) =
        authDataSource.socialLogin(socialType)

}