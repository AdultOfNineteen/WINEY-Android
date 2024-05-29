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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsAuthDataSource(
        authDataSource: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindsWineDataSource(
        wineDataSource: WineDataSourceImpl
    ): WineDataSource

    @Binds
    abstract fun bindsTastingNoteDataSource(
        tastingNoteDataSource: TastingNoteDataSourceImpl
    ): TastingNoteDataSource

    @Binds
    abstract fun bindsWineGradeDataSource(
        wineGradeDataSource: WineGradeDataSourceImpl
    ): WineGradeDataSource

    @Binds
    abstract fun bindsWineBadgeDataSource(
        wineBadgeDataSource: WineBadgeDataSourceImpl
    ): WineBadgeDataSource

    @Binds
    abstract fun bindsMapDataSource(
        mapDataSource: MapDataSourceImpl
    ): MapDataSource

    @Binds
    abstract fun bindsAuthRepository(
        authRepository: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindsWineRepository(
        wineRepository: WineRepositoryImpl
    ): WineRepository

    @Binds
    abstract fun bindsTastingNoteRepository(
        tastingNoteRepository: TastingNoteRepositoryImpl
    ): TastingNoteRepository

    @Binds
    abstract fun bindsDataStoreRepository(
        dataStoreRepository: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Binds
    abstract fun bindsWineGradeRepository(
        wineGradeRepository: WineGradeRepositoryImpl
    ): WineGradeRepository

    @Binds
    abstract fun bindsWineBadgeRepository(
        wineBadgeRepository: WineBadgeRepositoryImpl
    ): WineBadgeRepository

    @Binds
    abstract fun bindsMapRepository(
        mapRepository: MapRepositoryImpl
    ): MapRepository
}