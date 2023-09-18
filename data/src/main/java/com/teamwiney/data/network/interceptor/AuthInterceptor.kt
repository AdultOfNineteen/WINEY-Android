package com.teamwiney.data.network.interceptor

import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.data.repository.persistence.DataStoreRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = dataStoreRepository.getToken(ACCESS_TOKEN)
        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken").build()
        val response = chain.proceed(authenticationRequest)

        return response
    }

}