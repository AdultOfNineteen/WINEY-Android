package com.teamwiney.auth.splash

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.teamwiney.core.common.base.UiEffect
import com.teamwiney.core.common.base.UiEvent
import com.teamwiney.core.common.base.UiState

class SplashContract {

    data class State(
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent

    sealed class Effect : UiEffect {
        data class NavigateTo(
            val destination: String,
            val navOptions: NavOptions? = null,
            val builder: NavOptionsBuilder.() -> Unit = {}
        ) : Effect()

        data class ShowSnackBar(val message: String) : Effect()

        object CheckPermission : Effect()
    }
}