package com.teamwiney.auth.login

import android.content.Context
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState

class LoginContract {

    data class State(
        val isLoading: Boolean = false,
        val lastLoginMethod: String? = null,
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent {
        class KakaoLoginButtonClicked(val context: Context) : Event()
        class KakaoLoginSuccess(val token: String) : Event()
        class LoginFailed(val message: String) : Event()
        object GoogleLoginButtonClicked : Event()

    }

    sealed class Effect : UiEffect {
        object NavigateToHome : Effect()
        object NavigateToSignUp : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }

}