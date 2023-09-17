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
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.core.common.navigation.HomeDestinations
import com.teamwiney.core.common.util.Constants.ACCESS_TOKEN
import com.teamwiney.core.common.util.Constants.LOGIN_TYPE
import com.teamwiney.core.common.util.Constants.REFRESH_TOKEN
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.response.SocialLoginResponse
import com.teamwiney.data.network.service.SocialType
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.data.repository.persistence.DataStoreRepository
import com.teamwiney.feature.auth.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(
    initialState = LoginContract.State()
) {
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

    fun googleLogin(
        clientId: String = BuildConfig.GOOGLE_OAUTH_CLIENT_ID,
        clientSecret: String = BuildConfig.GOOGLE_OAUTH_CLIENT_SECRET,
        code: String,
        idToken: String
    ) {
        viewModelScope.launch {
            authRepository.getGoogleAccessToken(
                clientId = clientId,
                clientSecret = clientSecret,
                code = code,
                idToken = idToken
            ).onStart {
                updateState(currentState.copy(isLoading = true))
            }.collectLatest { result ->
                updateState(currentState.copy(isLoading = false))
                when (result) {
                    is ApiResult.Success -> {
                        socialLogin(SocialType.GOOGLE, result.data.accessToken)
                    }

                    is ApiResult.NetworkError -> {
                        updateState(currentState.copy(error = "네트워크 에러"))
                        postEffect(LoginContract.Effect.ShowSnackBar(currentState.error!!))
                    }

                    is ApiResult.ApiError -> {
                        updateState(currentState.copy(error = result.message))
                        postEffect(LoginContract.Effect.ShowSnackBar(currentState.error!!))
                    }
                }
            }
        }
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
                    // TODO : 토큰 저장 로직
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
                            val userStatus = result.data.result.userStatus
                            viewModelScope.launch {
                                dataStoreRepository.setToken(
                                    ACCESS_TOKEN,
                                    result.data.result.accessToken
                                )
                                dataStoreRepository.setToken(
                                    REFRESH_TOKEN,
                                    result.data.result.refreshToken
                                )
                                dataStoreRepository.setToken(LOGIN_TYPE, socialType.name)
                            }
                            if (userStatus == SocialLoginResponse.USER_STATUS_ACTIVE) {
                                postEffect(LoginContract.Effect.NavigateTo(
                                    destination = HomeDestinations.ROUTE,
                                    navOptions = navOptions {
                                        popUpTo(AuthDestinations.Login.ROUTE) {
                                            inclusive = true
                                        }
                                    }
                                ))
                            } else {
                                postEffect(LoginContract.Effect.NavigateTo("${AuthDestinations.SignUp.ROUTE}/${result.data.result.userId}"))
                            }
                        }

                        is ApiResult.NetworkError -> {
                            updateState(currentState.copy(error = "네트워크 에러"))
                            postEffect(LoginContract.Effect.ShowSnackBar(currentState.error!!))
                        }

                        is ApiResult.ApiError -> {
                            updateState(currentState.copy(error = result.message))
                            postEffect(LoginContract.Effect.ShowSnackBar(currentState.error!!))
                        }
                    }
                }
        }
    }
}