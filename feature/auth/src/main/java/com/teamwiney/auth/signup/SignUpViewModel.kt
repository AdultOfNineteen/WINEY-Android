package com.teamwiney.auth.signup

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.AuthDestinations
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.ui.signup.state.SignUpFavoriteCategoryiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(

) : BaseViewModel<SignUpContract.State, SignUpContract.Event, SignUpContract.Effect>(
    initialState = SignUpContract.State()
) {

    // TODO : 이벤트의 범위를 어디까지 해야할지 모르겠다....
    override fun reduceState(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.SendAuthenticationCode -> {
                viewModelScope.launch {
                    postEffect(
                        SignUpContract.Effect.ShowBottomSheet(
                            SignUpContract.BottomSheet.SendMessage
                        )
                    )
                }
            }
            is SignUpContract.Event.CancelAuthenticationCode -> {
                viewModelScope.launch {
                    postEffect(
                        SignUpContract.Effect.ShowBottomSheet(
                            SignUpContract.BottomSheet.ReturnToLogin
                        )
                    )
                }
            }
            is SignUpContract.Event.CancelTasteSelection -> {
                viewModelScope.launch {
                    postEffect(
                        SignUpContract.Effect.ShowBottomSheet(
                            SignUpContract.BottomSheet.CancelTasteSelection
                        )
                    )
                }
            }
            is SignUpContract.Event.SignUp -> {
                viewModelScope.launch {
                    postEffect(
                        SignUpContract.Effect.NavigateTo(AuthDestinations.SignUp.COMPLETE)
                    )
                }
            }
        }
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

