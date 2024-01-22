package com.teamwiney.auth.splash

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.model.UserStatus
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.core.common.util.Constants.IS_NOT_FIRST_LAUNCH
import com.teamwiney.core.common.util.Constants.USER_ID
import com.teamwiney.data.di.DispatcherModule
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                SplashContract.Event.CheckUserStatus -> {
                    checkUserStatus()
                }
            }
        }
    }

    fun checkIsFirstLaunch() = viewModelScope.launch {
        val isNotFirstLaunch = dataStoreRepository.getBooleanValue(IS_NOT_FIRST_LAUNCH).first()

        if (!isNotFirstLaunch) {
            postEffect(SplashContract.Effect.CheckPermission)
            dataStoreRepository.setBooleanValue(IS_NOT_FIRST_LAUNCH, true)
        }
    }

    private fun checkUserStatus() = viewModelScope.launch {
        authRepository.getUserInfo().collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    val userInfo = it.data.result

                    runBlocking { dataStoreRepository.setIntValue(USER_ID, userInfo.userId) }

                    if (userInfo.status == UserStatus.ACTIVE) {
                        getConnections()
                        navigateToMain()
                    } else {
                        naviagateToLogin()
                    }
                }

                is ApiResult.ApiError -> {
                    naviagateToLogin()
                }

                is ApiResult.NetworkError -> {
                    postEffect(SplashContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }
            }
        }
    }

    private fun getConnections() = viewModelScope.launch {
        authRepository.getConnections().collectLatest {
            when (it) {
                is ApiResult.ApiError -> {
                    postEffect(SplashContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(SplashContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }

                else -> { }
            }
        }
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