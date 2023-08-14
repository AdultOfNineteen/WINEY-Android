package com.teamwiney.data.auth.repository

import com.teamwiney.core.network.adapter.ApiResult
import com.teamwiney.core.network.model.response.ResponseWrapper
import com.teamwiney.core.network.service.SocialType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun socialLogin(socialType: SocialType): Flow<ApiResult<ResponseWrapper<String>>>

}