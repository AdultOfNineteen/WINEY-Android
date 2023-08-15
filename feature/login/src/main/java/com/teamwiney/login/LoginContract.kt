package com.teamwiney.login

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
        object KaKaoLoginButtonClicked : Event()
        object GoogleLoginButtonClicked : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToHome : Effect()
        object NavigateToSignUp : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }

}