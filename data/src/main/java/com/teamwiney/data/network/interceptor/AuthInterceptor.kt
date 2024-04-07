package com.teamwiney.data.network.interceptor

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.teamwiney.core.common.base.CommonResponse
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.core.common.util.Constants.REFRESH_TOKEN
import com.teamwiney.data.BuildConfig
import com.teamwiney.data.network.model.response.AccessToken
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { dataStoreRepository.getStringValue(ACCESS_TOKEN).first() }
        val refreshToken = runBlocking { dataStoreRepository.getStringValue(REFRESH_TOKEN).first() }

        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder()
            .addHeader("X-AUTH-TOKEN", accessToken)
            .build()

        val response = chain.proceed(authenticationRequest)

        when (response.code) {
            401 -> {
                val newToken = runBlocking { updateToken(refreshToken, context) }

                if (newToken.isSuccessful) {
                    val newAccessToken = newToken.body()?.result?.accessToken ?: ""

                    runBlocking { dataStoreRepository.setStringValue(ACCESS_TOKEN, newAccessToken) }

                    val newAuthenticationRequest = originalRequest.newBuilder()
                        .header("X-AUTH-TOKEN", newAccessToken)
                        .build()

                    return chain.proceed(newAuthenticationRequest)
                } else {
                    runBlocking {
                        dataStoreRepository.deleteStringValue(ACCESS_TOKEN)
                        dataStoreRepository.deleteStringValue(REFRESH_TOKEN)
                    }
                }
            }
        }

        return response
    }
}

private suspend fun updateToken(
    refreshToken: String,
    context: Context
): retrofit2.Response<CommonResponse<AccessToken>> {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient(refreshToken, context))
        .build()
    val service = retrofit.create(AuthService::class.java)

    return service.updateToken()
}

private fun createOkHttpClient(
    refreshToken: String,
    context: Context
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }
        )
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("X-REFRESH-TOKEN", refreshToken)
                .build()

            chain.proceed(modifiedRequest)
        }

    if (BuildConfig.DEBUG) {
        builder.addNetworkInterceptor(ChuckerInterceptor(context))
    }

    return builder.build()
}
