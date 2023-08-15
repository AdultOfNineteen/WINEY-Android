package com.teamwiney.data.di

import com.teamwiney.data.datasource.AuthDataSource
import com.teamwiney.data.datasource.AuthDataSourceImpl
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.repository.AuthRepository
import com.teamwiney.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAuthDataSource(authService: AuthService) : AuthDataSource =
        AuthDataSourceImpl(authService)

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource) : AuthRepository =
        AuthRepositoryImpl(authDataSource)

}