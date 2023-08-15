package com.teamwiney.login

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

    private val TAG = "LoginViewModel"
    override fun reduceState(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.KaKaoLoginButtonClicked -> {
                socialLogin(SocialType.KAKAO)
            }

            is LoginContract.Event.GoogleLoginButtonClicked -> {
                // socialLogin(SocialType.GOOGLE)
            }
        }
    }

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    fun kakaoLogin(context: Context, onSuccess: () -> Unit) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    socialLogin(SocialType.KAKAO)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun socialLogin(socialType: SocialType) {
        viewModelScope.launch {
            authRepository.socialLogin(socialType, "")
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