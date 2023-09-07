package com.teamwiney.auth.login

import android.content.Context
import androidx.navigation.NavOptions
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
        object GoogleLoginButtonClicked : Event()

    }

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null
        ) : Effect()
        data class ShowSnackBar(val message: String) : Effect()
        object LaunchGoogleLogin : Effect()
    }

}