package com.teamwiney.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.service.SocialType
import com.teamwiney.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
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
                }

                is LoginContract.Event.KakaoLoginSuccess -> {
                    // TODO 엑세스 토큰 저장 로직 필요
                    postEffect(LoginContract.Effect.NavigateToHome)
                }

                is LoginContract.Event.LoginFailed -> {
                    updateState(currentState.copy(error = event.message))
                    postEffect(LoginContract.Effect.ShowSnackbar(currentState.error!!))
                }
            }
        }
    }

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            processEvent(LoginContract.Event.LoginFailed("카카오톡으로 로그인 실패"))
        } else if (token != null) {
            Log.i("LoginViewModel", "kakao token: ${token.accessToken}")
            Log.i("LoginViewModel", "kakao idToken: ${token.idToken}")
            socialLogin(SocialType.KAKAO, token.accessToken)
        }
    }

    private fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        viewModelScope.launch {
                            updateState(currentState.copy(error = "카카오톡으로 로그인 실패"))
                            postEffect(LoginContract.Effect.ShowSnackbar(currentState.error!!))
                        }
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("LoginViewModel", "kakao token: ${token.accessToken}")
                    Log.i("LoginViewModel", "kakao idToken: ${token.idToken}")
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
                }
                .collect { result ->
                    updateState(currentState.copy(isLoading = false))
                    when (result) {
                        is ApiResult.Success -> {
                            postEffect(LoginContract.Effect.NavigateToHome)
                        }

                        is ApiResult.NetworkError -> {
                            updateState(currentState.copy(error = "네트워크 에러"))
                            postEffect(LoginContract.Effect.ShowSnackbar(currentState.error!!))
                        }

                        is ApiResult.ApiError -> {
                            updateState(currentState.copy(error = result.message))
                            postEffect(LoginContract.Effect.ShowSnackbar(currentState.error!!))
                        }
                    }
                }
        }
    }
}