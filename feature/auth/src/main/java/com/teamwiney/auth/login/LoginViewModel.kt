package com.teamwiney.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.model.MessageStatus
import com.teamwiney.core.common.model.PreferenceStatus
import com.teamwiney.core.common.model.SocialType
import com.teamwiney.core.common.model.UserStatus
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.core.common.util.Constants.LOGIN_TYPE
import com.teamwiney.core.common.util.Constants.REFRESH_TOKEN
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(
    initialState = LoginContract.State()
) {
    init {
        val lastLoginType = runBlocking { dataStoreRepository.getStringValue(LOGIN_TYPE).first() }
        updateState(
            currentState.copy(
                lastLoginMethod = when (lastLoginType) {
                    SocialType.KAKAO.name -> "카카오"
                    SocialType.GOOGLE.name -> "구글"
                    else -> null
                }
            )
        )
    }

    override fun reduceState(event: LoginContract.Event) {
        viewModelScope.launch {
            when (event) {
                is LoginContract.Event.KakaoLoginButtonClicked -> {
                    kakaoLogin(event.context)
                }

                is LoginContract.Event.GoogleLoginButtonClicked -> {
                    postEffect(LoginContract.Effect.LaunchGoogleLogin)
                }
            }
        }
    }

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            viewModelScope.launch {
                error.printStackTrace()
                postEffect(LoginContract.Effect.ShowSnackBar("카카오톡으로 로그인 실패"))
            }
        } else if (token != null) {
            socialLogin(SocialType.KAKAO, token.accessToken)
        }
    }

    fun googleLogin(token: String) {
        socialLogin(
            socialType = SocialType.GOOGLE,
            token = token
        )
    }

    private fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    error.printStackTrace()
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        viewModelScope.launch {
                            postEffect(LoginContract.Effect.ShowSnackBar("카카오톡으로 로그인 실패"))
                        }
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("LoginViewModel", "accessToken: ${token.accessToken}")
                    socialLogin(SocialType.KAKAO, token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun socialLogin(socialType: SocialType, token: String) {
        viewModelScope.launch {
            authRepository.socialLogin(socialType, token)
                .onStart {
                    updateState(currentState.copy(isLoading = true))
                }.collectLatest { result ->
                    updateState(currentState.copy(isLoading = false))
                    when (result) {
                        is ApiResult.Success -> {
                            runBlocking {
                                dataStoreRepository.setStringValue(ACCESS_TOKEN, result.data.result.accessToken)
                                dataStoreRepository.setStringValue(REFRESH_TOKEN, result.data.result.refreshToken)
                                dataStoreRepository.setIntValue(Constants.USER_ID, result.data.result.userId)
                            }

                            Log.i(
                                "[ACCESS_TOKEN]",
                                "accessToken: ${result.data.result.accessToken}"
                            )
                            Log.i(
                                "[REFRESH_TOKEN]",
                                "refreshToken: ${result.data.result.refreshToken}"
                            )

                            val userStatus = result.data.result.userStatus
                            val messageStatus = result.data.result.messageStatus
                            val preferenceStatus = result.data.result.preferenceStatus

                            if (userStatus == UserStatus.ACTIVE) {
                                dataStoreRepository.setStringValue(LOGIN_TYPE, socialType.name)

                                registerFcmToken()

                                postEffect(LoginContract.Effect.NavigateTo(
                                    destination = HomeDestinations.ROUTE,
                                    navOptions = navOptions {
                                        popUpTo(AuthDestinations.Login.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                ))
                            } else {
                                if (messageStatus != MessageStatus.VERIFIED) {
                                    postEffect(LoginContract.Effect.NavigateTo(AuthDestinations.SignUp.ROUTE))
                                } else if (preferenceStatus != PreferenceStatus.DONE) {
                                    postEffect(LoginContract.Effect.NavigateTo(AuthDestinations.SignUp.FAVORITE_TASTE))
                                } else {
                                    postEffect(LoginContract.Effect.NavigateTo(AuthDestinations.SignUp.ROUTE))
                                }
                            }
                        }

                        is ApiResult.ApiError -> {
                            postEffect(LoginContract.Effect.ShowSnackBar(result.message))
                        }

                        is ApiResult.NetworkError -> {
                            postEffect(LoginContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                        }
                    }
                }
        }
    }

    private fun registerFcmToken() = viewModelScope.launch {
        val fcmToken = dataStoreRepository.getStringValue(Constants.FCM_TOKEN).first()
        val deviceId = dataStoreRepository.getStringValue(Constants.DEVICE_ID).first()

        authRepository.registerFcmToken(fcmToken, deviceId).collectLatest {
            when (it) {
                is ApiResult.ApiError -> {
                    postEffect(LoginContract.Effect.ShowSnackBar(it.message))
                }

                is ApiResult.NetworkError -> {
                    postEffect(LoginContract.Effect.ShowSnackBar("네트워크 오류가 발생했습니다."))
                }

                else -> { }
            }
        }
    }

}