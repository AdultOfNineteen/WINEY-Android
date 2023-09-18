package com.teamwiney.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.teamwiney.data.datasource.AuthDataSource
import com.teamwiney.data.datasource.AuthDataSourceImpl
import com.teamwiney.data.datasource.WineDataSource
import com.teamwiney.data.datasource.WineDataSourceImpl
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.network.service.WineService
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.auth.AuthRepositoryImpl
import com.teamwiney.data.repository.auth.WineRepository
import com.teamwiney.data.repository.auth.WineRepositoryImpl
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.persistence.DataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesAuthDataSource(
        authService: AuthService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthDataSource =
        AuthDataSourceImpl(authService, ioDispatcher)

    @Provides
    @Singleton
    fun providesWineDataSource(
        wineService: WineService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WineDataSource =
        WineDataSourceImpl(wineService, ioDispatcher)

    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)

    @Provides
    @Singleton
    fun providesWineRepository(wineDataSource: WineDataSource): WineRepository =
        WineRepositoryImpl(wineDataSource)

    @Provides
    @Singleton
    fun providesDataStoreRepository(
        pref: DataStore<Preferences>
    ): DataStoreRepository = DataStoreRepositoryImpl(pref)

}