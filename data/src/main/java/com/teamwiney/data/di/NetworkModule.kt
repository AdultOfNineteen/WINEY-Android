package com.teamwiney.data.di

import com.teamwiney.data.BuildConfig
import com.teamwiney.data.network.adapter.ApiResultCallAdapterFactory
import com.teamwiney.data.network.converter.EnumConverterFactory
import com.teamwiney.data.network.interceptor.AuthInterceptor
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.network.service.WineService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    }
            )
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .addConverterFactory(EnumConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit) =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesWineService(retrofit: Retrofit) =
        retrofit.create(WineService::class.java)

}