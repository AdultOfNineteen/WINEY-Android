package com.teamwiney.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.teamwiney.data.datasource.AuthDataSource
import com.teamwiney.data.datasource.AuthDataSourceImpl
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.auth.AuthRepositoryImpl
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.persistence.DataStoreRepositoryImpl
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
    fun providesAuthDataSource(
        authService: AuthService,
        @DispatcherModule.IoDispatcher ioDispatcher: kotlinx.coroutines.CoroutineDispatcher
    ): AuthDataSource =
        AuthDataSourceImpl(authService, ioDispatcher)

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun providesDatStoreRepository(
        pref: DataStore<Preferences>
    ): DataStoreRepository = DataStoreRepositoryImpl(pref)
}