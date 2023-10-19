package com.teamwiney.auth.splash

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
    @DispatcherModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>(
    initialState = SplashContract.State()
) {
    init {
        viewModelScope.launch {
            dataStoreRepository.setBooleanValue(Constants.IS_FIRST_SCROLL, true)
        }
    }


    override fun reduceState(event: SplashContract.Event) {
        viewModelScope.launch {
            when (event) {
                SplashContract.Event.AutoLoginCheck -> {
                    autoLoginCheck()
                }
            }
        }
    }

    private fun autoLoginCheck() = viewModelScope.launch {
        val accessToken = runBlocking { dataStoreRepository.getStringValue(ACCESS_TOKEN).first() }

        if (accessToken.isNotEmpty()) {
            postEffect(SplashContract.Effect.NavigateTo(HomeDestinations.ROUTE) {
                popUpTo(AuthDestinations.SPLASH) { inclusive = true }
            })
        } else {
            postEffect(SplashContract.Effect.NavigateTo(AuthDestinations.Login.ROUTE) {
                popUpTo(AuthDestinations.SPLASH) { inclusive = true }
            })
        }
    }
}