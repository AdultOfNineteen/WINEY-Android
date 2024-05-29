package com.teamwiney.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.teamwiney.core.common.di.DispatcherModule
import com.teamwiney.data.datasource.auth.AuthDataSource
import com.teamwiney.data.datasource.auth.AuthDataSourceImpl
import com.teamwiney.data.datasource.map.MapDataSource
import com.teamwiney.data.datasource.map.MapDataSourceImpl
import com.teamwiney.data.datasource.tastingnote.TastingNoteDataSource
import com.teamwiney.data.datasource.tastingnote.TastingNoteDataSourceImpl
import com.teamwiney.data.datasource.wine.WineDataSource
import com.teamwiney.data.datasource.wine.WineDataSourceImpl
import com.teamwiney.data.datasource.winebadge.WineBadgeDataSource
import com.teamwiney.data.datasource.winebadge.WineBadgeDataSourceImpl
import com.teamwiney.data.datasource.winegrade.WineGradeDataSource
import com.teamwiney.data.datasource.winegrade.WineGradeDataSourceImpl
import com.teamwiney.data.network.service.AuthService
import com.teamwiney.data.network.service.MapService
import com.teamwiney.data.network.service.TastingNoteService
import com.teamwiney.data.network.service.WineBadgeService
import com.teamwiney.data.network.service.WineGradeService
import com.teamwiney.data.network.service.WineService
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.auth.AuthRepositoryImpl
import com.teamwiney.data.repository.map.MapRepository
import com.teamwiney.data.repository.map.MapRepositoryImpl
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.data.repository.persistence.DataStoreRepositoryImpl
import com.teamwiney.data.repository.tastingnote.TastingNoteRepository
import com.teamwiney.data.repository.tastingnote.TastingNoteRepositoryImpl
import com.teamwiney.data.repository.wine.WineRepository
import com.teamwiney.data.repository.wine.WineRepositoryImpl
import com.teamwiney.data.repository.winebadge.WineBadgeRepository
import com.teamwiney.data.repository.winebadge.WineBadgeRepositoryImpl
import com.teamwiney.data.repository.winegrade.WineGradeRepository
import com.teamwiney.data.repository.winegrade.WineGradeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesTastingNoteDataSource(
        tastingNoteService: TastingNoteService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TastingNoteDataSource =
        TastingNoteDataSourceImpl(tastingNoteService, ioDispatcher)

    @Provides
    @Singleton
    fun providesWineGradeDataSource(
        wineGradeService: WineGradeService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WineGradeDataSource =
        WineGradeDataSourceImpl(wineGradeService, ioDispatcher)

    @Provides
    @Singleton
    fun providesWineBadgeDataSource(
        wineBadgeService: WineBadgeService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WineBadgeDataSource =
        WineBadgeDataSourceImpl(wineBadgeService, ioDispatcher)

    @Provides
    @Singleton
    fun providesMapDataSource(
        mapService: MapService,
        @DispatcherModule.IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MapDataSource =
        MapDataSourceImpl(mapService, ioDispatcher)

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
    fun providesTastingNotRepository(
        tastingNoteDataSource: TastingNoteDataSource,
        @ApplicationContext context: Context
    ): TastingNoteRepository = TastingNoteRepositoryImpl(tastingNoteDataSource, context)

    @Provides
    @Singleton
    fun providesDataStoreRepository(
        pref: DataStore<Preferences>
    ): DataStoreRepository = DataStoreRepositoryImpl(pref)

    @Provides
    @Singleton
    fun providesWineGradeRepository(
        wineGradeDataSource: WineGradeDataSource
    ): WineGradeRepository = WineGradeRepositoryImpl(wineGradeDataSource)

    @Provides
    @Singleton
    fun providesWineBadgeRepository(
        wineBadgeDataSource: WineBadgeDataSource
    ): WineBadgeRepository = WineBadgeRepositoryImpl(wineBadgeDataSource)

    @Provides
    @Singleton
    fun providesMapRepository(
        mapDataSource: MapDataSource
    ): MapRepository = MapRepositoryImpl(mapDataSource)
}