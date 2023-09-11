package com.teamwiney.auth.signup

import androidx.lifecycle.viewModelScope
import com.teamwiney.core.common.base.BaseViewModel
import com.teamwiney.core.common.navigation.AuthDestinations
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.PhoneNumberRequest
import com.teamwiney.data.network.model.request.PhoneNumberWithVerificationCodeRequest
import com.teamwiney.data.repository.auth.AuthRepository
import com.teamwiney.ui.signup.state.SignUpFavoriteCategoryiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<SignUpContract.State, SignUpContract.Event, SignUpContract.Effect>(
    initialState = SignUpContract.State()
) {

    override fun reduceState(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.SendAuthenticationButtonClicked -> {
                sendAuthenticationNumber()
            }

            is SignUpContract.Event.BackToLoginButtonClicked -> {
                postEffect(
                    SignUpContract.Effect.ShowBottomSheet(
                        SignUpContract.BottomSheet.ReturnToLogin
                    )
                )
            }

            is SignUpContract.Event.CancelTasteSelectionButtonClicked -> {
                postEffect(
                    SignUpContract.Effect.ShowBottomSheet(
                        SignUpContract.BottomSheet.CancelTasteSelection
                    )
                )
            }

            is SignUpContract.Event.TasteSelectionLastItemClicked -> {
                postEffect(
                    SignUpContract.Effect.NavigateTo(AuthDestinations.SignUp.COMPLETE)
                )
            }

            is SignUpContract.Event.VerifyCode -> {
                verifyAuthenticationNumber()
            }
        }
    }

    private fun verifyAuthenticationNumber() = viewModelScope.launch {
        authRepository.verifyAuthCodeMessage(
            currentState.userId,
            PhoneNumberWithVerificationCodeRequest(
                phoneNumber = currentState.phoneNumber,
                verificationCode = currentState.verifyNumber
            )
        ).collectLatest {
            when (it) {
                is ApiResult.Success -> {
                    postEffect(SignUpContract.Effect.ShowBottomSheet(SignUpContract.BottomSheet.SendMessage))
                }

                is ApiResult.ApiError -> {
                    updateState(
                        currentState.copy(
                            verifyNumberErrorState = true,
                            verifyNumberErrorText = "인증번호를 확인해 주세요."
                        )
                    )
                    postEffect(SignUpContract.Effect.ShowSnackBar("인증번호를 확인해 주세요."))
                }

                else -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 에러가 발생했습니다."))
                }
            }
        }
    }

    private fun sendAuthenticationNumber() = viewModelScope.launch {
        authRepository.sendAuthCodeMessage(
            currentState.userId,
            PhoneNumberRequest(
                currentState.phoneNumber
            )
        ).onStart {
            updateState(currentState.copy(isLoading = true))
        }.collectLatest {
            updateState(currentState.copy(isLoading = false))
            when (it) {
                is ApiResult.Success -> {
                    postEffect(
                        SignUpContract.Effect.ShowBottomSheet(
                            SignUpContract.BottomSheet.SendMessage
                        )
                    )
                }

                else -> {
                    postEffect(SignUpContract.Effect.ShowSnackBar("네트워크 에러가 발생했습니다."))
                }
            }
        }
    }

    fun updateUserId(userId: String) = viewModelScope.launch {
        updateState(currentState.copy(userId = userId))
    }

    fun updateVerifyNumberErrorText(text: String) = viewModelScope.launch {
        updateState(currentState.copy(verifyNumberErrorText = text))
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

