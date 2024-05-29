package com.teamwiney.core.common.di

import android.content.Context
import com.teamwiney.core.common.ConnectivityManagerNetworkMonitor
import com.teamwiney.core.common.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object NetworkMonitorModule {

    @Provides
    fun providesNetworkMonitor(
        @ApplicationContext context: Context,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NetworkMonitor =
        ConnectivityManagerNetworkMonitor(context, ioDispatcher)

}