package com.teamwiney.signup

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.domain.SignUpContract
import com.teamwiney.ui.signup.state.SignUpFavoriteCategoryiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : BaseViewModel<SignUpContract.State, SignUpContract.Event, SignUpContract.Effect>(
    initialState = SignUpContract.State()
) {
    override fun reduceState(event: SignUpContract.Event) {
        when (event) {
            SignUpContract.Event.GoogleLoginButtonClicked -> TODO()
            SignUpContract.Event.KaKaoLoginButtonClicked -> TODO()
            SignUpContract.Event.NaverLoginButtonClicked -> TODO()
        }
    }

    fun senVericiationCode(onSuccess: () -> Unit) = viewModelScope.launch {
        // TODO : sendVerificationCode
        //      제가 하던 콜백방식이라 원하시면 바꾸셔도됩니다.
        onSuccess()
    }

    fun signUp(onSuccess: () -> Unit) = viewModelScope.launch {
        // TODO("회원가입 진행하기")
        onSuccess()
    }

    fun updatePhoneNumber(phoneNumber: String) = viewModelScope.launch {
        updateState(currentState.copy(phoneNumber = phoneNumber))
    }

    fun updatePhoneNumberErrorState(isError: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(phoneNumberErrorState = isError))
    }

    fun updateVerifyNumber(verifyNumber: String) = viewModelScope.launch {
        updateState(currentState.copy(verifyNumber = verifyNumber))
    }

    fun updateVerifyNumberErrorState(isError: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(verifyNumberErrorState = isError))
    }

    fun updateRemainingTime(remainingTime: Int) = viewModelScope.launch {
        updateState(currentState.copy(remainingTime = remainingTime))
    }

    fun updateIsTimerRunning(isTimerRunning: Boolean) = viewModelScope.launch {
        updateState(currentState.copy(isTimerRunning = isTimerRunning))
    }

    fun resetTimer() = viewModelScope.launch {
        updateState(
            currentState.copy(
                remainingTime = SignUpContract.VERIFY_NUMBER_TIMER,
                isTimerRunning = true
            )
        )
    }

    fun updateSignUpFavoriteItem(signUpFavoriteCategoryiState: SignUpFavoriteCategoryiState) =
        viewModelScope.launch {
            updateState(
                currentState.copy(
                    favoriteTastes = currentState.favoriteTastes.map {
                        if (it.title == signUpFavoriteCategoryiState.title) {
                            signUpFavoriteCategoryiState
                        } else {
                            it
                        }
                    }
                )
            )
        }
}

