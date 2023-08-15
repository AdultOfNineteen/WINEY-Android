package com.teamwiney.login

import androidx.lifecycle.viewModelScope
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
        when (event) {
            is LoginContract.Event.KaKaoLoginButtonClicked -> {
                socialLogin(SocialType.KAKAO)
            }
            is LoginContract.Event.NaverLoginButtonClicked -> {
                // 미구현
            }
            is LoginContract.Event.GoogleLoginButtonClicked -> {
                socialLogin(SocialType.GOOGLE)
            }
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