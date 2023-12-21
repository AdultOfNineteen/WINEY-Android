package com.teamwiney.auth.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.core.common.util.Constants.REFRESH_TOKEN
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
        val refreshToken =
            withContext(ioDispatcher) { dataStoreRepository.getStringValue(REFRESH_TOKEN).first() }
        Log.i("[REFRESH_TOKEN] : ", refreshToken)
        if (refreshToken.isNotEmpty()) {
            refreshToken(refreshToken)
        } else {
            naviagateToLogin()
        }
    }

    private fun refreshToken(refreshToken: String) = viewModelScope.launch {
        authRepository.refreshToken(refreshToken).collectLatest { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val accessToken = apiResult.data.result.accessToken
                    withContext(ioDispatcher) {
                        dataStoreRepository.setStringValue(ACCESS_TOKEN, accessToken)
                    }
                    navigateToMain()
                }

                else -> {
                    naviagateToLogin()
                }
            }
        }
    }

    fun registerFcmToken(
        fcmToken: String,
        deviceId: String
    ) = viewModelScope.launch {
        authRepository.registerFcmToken(fcmToken, deviceId)
    }

    fun getConnections() = viewModelScope.launch {
        authRepository.getConnections()
    }

    private fun navigateToMain() {
        postEffect(SplashContract.Effect.NavigateTo(HomeDestinations.ROUTE) {
            popUpTo(AuthDestinations.SPLASH) { inclusive = true }
        })
    }

    private fun naviagateToLogin() {
        postEffect(SplashContract.Effect.NavigateTo(AuthDestinations.Login.ROUTE) {
            popUpTo(AuthDestinations.SPLASH) { inclusive = true }
        })
    }
}